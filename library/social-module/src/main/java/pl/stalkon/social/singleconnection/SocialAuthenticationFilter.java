package pl.stalkon.social.singleconnection;

/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.security.SocialAuthenticationRedirectException;
import org.springframework.social.security.SocialAuthenticationServiceLocator;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.social.security.provider.SocialAuthenticationService;
import org.springframework.util.Assert;

import pl.stalkon.social.singleconnection.interfaces.ConnectionRepository;
import pl.stalkon.social.singleconnection.interfaces.SocialUserDetails;
import pl.stalkon.social.singleconnection.interfaces.UserIdSource;
import pl.stalkon.social.singleconnection.interfaces.UsersConnectionRepository;

/**
 * Filter for handling the provider sign-in flow within the Spring Security
 * filter chain. Should be injected into the chain at or before the
 * PRE_AUTH_FILTER location.
 * 
 * @author Stefan Fussenegger
 * @author Craig Walls
 * @author Yuan Ji
 */
public class SocialAuthenticationFilter extends
		AbstractAuthenticationProcessingFilter {

	private SocialAuthenticationServiceLocator authServiceLocator;

	private String signupUrl = "/signup";

	private String connectionAddedRedirectUrl = "/";

	private boolean updateConnections = true;

	private UserIdSource userIdSource;

	private UsersConnectionRepository usersConnectionRepository;

	public SocialAuthenticationFilter(AuthenticationManager authManager,
			UserIdSource userIdSource,
			UsersConnectionRepository usersConnectionRepository,
			SocialAuthenticationServiceLocator authServiceLocator) {
		super("/auth");
		setAuthenticationManager(authManager);
		this.userIdSource = userIdSource;
		this.usersConnectionRepository = usersConnectionRepository;
		this.authServiceLocator = authServiceLocator;
		super.setAuthenticationFailureHandler(new SocialAuthenticationFailureHandler(
				new SimpleUrlAuthenticationFailureHandler()));
	}

	@Override
	public void setAuthenticationFailureHandler(
			AuthenticationFailureHandler failureHandler) {
		super.setAuthenticationFailureHandler(new SocialAuthenticationFailureHandler(
				new SimpleUrlAuthenticationFailureHandler()));
	}

	public void setSignupUrl(String signupUrl) {
		this.signupUrl = signupUrl;
	}

	public void setConnectionAddedRedirectUrl(String connectionAddedRedirectUrl) {
		this.connectionAddedRedirectUrl = connectionAddedRedirectUrl;
	}

	public void setUpdateConnections(boolean updateConnections) {
		this.updateConnections = updateConnections;
	}

	public void setPostLoginUrl(String postLoginUrl) {
		AuthenticationSuccessHandler successHandler = getSuccessHandler();
		if (successHandler instanceof AbstractAuthenticationTargetUrlRequestHandler) {
			AbstractAuthenticationTargetUrlRequestHandler h = (AbstractAuthenticationTargetUrlRequestHandler) successHandler;
			h.setDefaultTargetUrl(postLoginUrl);
		} else {
			throw new IllegalStateException(
					"can't set postLoginUrl on unknown successHandler, type is "
							+ successHandler.getClass().getName());
		}
	}

	public void setPostFailureUrl(String postFailureUrl) {
		AuthenticationFailureHandler failureHandler = getFailureHandler();
		if (failureHandler instanceof SimpleUrlAuthenticationFailureHandler) {
			SimpleUrlAuthenticationFailureHandler h = (SimpleUrlAuthenticationFailureHandler) failureHandler;
			h.setDefaultFailureUrl(postFailureUrl);
		} else {
			throw new IllegalStateException(
					"can't set postFailureUrl on unknown failureHandler, type is "
							+ failureHandler.getClass().getName());
		}
	}

	public UsersConnectionRepository getUsersConnectionRepository() {
		return usersConnectionRepository;
	}

	public SocialAuthenticationServiceLocator getAuthServiceLocator() {
		return authServiceLocator;
	}

	/**
	 * Indicates whether this filter should attempt to process a social network
	 * login request for the current invocation.
	 * <p>
	 * Check if request URL matches filterProcessesUrl with valid providerId.
	 * The URL must be like {filterProcessesUrl}/{providerId}.
	 * 
	 * @return <code>true</code> if the filter should attempt authentication,
	 *         <code>false</code> otherwise.
	 */
	protected boolean requiresAuthentication(HttpServletRequest request,
			HttpServletResponse response) {
		Authentication auth = getAuthentication();
		if(auth != null && auth.isAuthenticated()){
			return false;
		}
		String providerId = getRequestedProviderId(request);
		if (providerId != null) {
			Set<String> authProviders = authServiceLocator
					.registeredAuthenticationProviderIds();
			return authProviders.contains(providerId);
		}
		return false;
	}


	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		Authentication auth = null;
		Set<String> authProviders = authServiceLocator
				.registeredAuthenticationProviderIds();
		String authProviderId = getRequestedProviderId(request);
		if (!authProviders.isEmpty() && authProviderId != null
				&& authProviders.contains(authProviderId)) {
			SocialAuthenticationService<?> authService = authServiceLocator
					.getAuthenticationService(authProviderId);
			auth = attemptAuthService(authService, request, response);
			if (auth == null) {
				throw new AuthenticationServiceException(
						"authentication failed");
			}
		}
		return auth;
	}

	// private helpers
	private Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/*
	 * Call SocialAuthenticationService.getAuthToken() to get
	 * SocialAuthenticationToken: If first phase, throw
	 * AuthenticationRedirectException to redirect to provider website. If
	 * second phase, get token/code from request parameter and call provider API
	 * to get accessToken/accessGrant. Check Authentication object in spring
	 * security context, if null or not authenticated, call doAuthentication()
	 * Otherwise, it is already authenticated, add this connection.
	 */
	private Authentication attemptAuthService(
			final SocialAuthenticationService<?> authService,
			final HttpServletRequest request, HttpServletResponse response)
			throws SocialAuthenticationRedirectException,
			AuthenticationException {
		final SocialAuthenticationToken token = authService.getAuthToken(
				request, response);
		if (token == null)
			return null;
		Assert.notNull(token.getConnection());

		Authentication auth = getAuthentication();
		if (auth == null || !auth.isAuthenticated()) {
			return doAuthentication(authService, request, token);
		} 
		return null;
	}

	private String getRequestedProviderId(HttpServletRequest request) {
		String uri = request.getRequestURI();
		int pathParamIndex = uri.indexOf(';');

		if (pathParamIndex > 0) {
			// strip everything after the first semi-colon
			uri = uri.substring(0, pathParamIndex);
		}

		// uri must start with context path
		uri = uri.substring(request.getContextPath().length());

		// remaining uri must start with filterProcessesUrl
		if (!uri.startsWith(getFilterProcessesUrl())) {
			return null;
		}
		uri = uri.substring(getFilterProcessesUrl().length());

		// expect /filterprocessesurl/provider, not /filterprocessesurlproviderr
		if (uri.startsWith("/")) {
			return uri.substring(1);
		} else {
			return null;
		}
	}

	private Authentication doAuthentication(
			SocialAuthenticationService<?> authService,
			HttpServletRequest request, SocialAuthenticationToken token) {
		try {
//			if (!authService.getConnectionCardinality()
//					.isAuthenticatePossible())
//				return null;
			token.setDetails(authenticationDetailsSource.buildDetails(request));
			Authentication success = getAuthenticationManager().authenticate(
					token);
			Assert.isInstanceOf(SocialUserDetails.class,
					success.getPrincipal(), "unexpected principle type");
			updateConnections(authService, token, success);
			return success;
		} catch (BadCredentialsException e) {
			// connection unknown, register new user?
			if (signupUrl != null) {
				// store ConnectionData in session and redirect to register page
				addSignInAttempt(request.getSession(), token.getConnection());
				throw new SocialAuthenticationRedirectException(signupUrl);
			}
			throw e;
		}
	}

	private void updateConnections(SocialAuthenticationService<?> authService,
			SocialAuthenticationToken token, Authentication success) {
		if (updateConnections) {
			Long userId = ((SocialUserDetails) success.getPrincipal())
					.getUserId();
			Connection<?> connection = token.getConnection();
			ConnectionRepository repo = getUsersConnectionRepository()
					.createConnectionRepository(userId);
			repo.updateConnection(connection);
		}
	}

	private void addSignInAttempt(HttpSession session, Connection<?> connection) {
		session.setAttribute(ProviderSignInAttempt.SESSION_ATTRIBUTE,
				new ProviderSignInAttempt(connection, authServiceLocator,
						usersConnectionRepository));
	}

}
