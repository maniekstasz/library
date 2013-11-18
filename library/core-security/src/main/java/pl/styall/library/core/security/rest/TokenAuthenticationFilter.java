package pl.styall.library.core.security.rest;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

public class TokenAuthenticationFilter extends GenericFilterBean {

	private Logger logger = Logger.getLogger(TokenAuthenticationFilter.class);

	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
	private AuthenticationManager authenticationManager;

	private AuthenticationEntryPoint authenticationEntryPoint;
	
	private AuthenticationResultHandler authenticationResultHandler;
	private final boolean ignoreFailure;

	public TokenAuthenticationFilter(
			AuthenticationManager authenticationManager,
			AuthenticationEntryPoint authenticationEntryPoint) {
		this.authenticationManager = authenticationManager;
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.ignoreFailure = false;
	}

	public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		this.ignoreFailure = true;
	}

	public TokenAuthenticationFilter() {
		this.ignoreFailure = true;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		final boolean debug = logger.isDebugEnabled();

		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;

		String header = request.getHeader("AuthToken");
		if (header == null) {
			chain.doFilter(req, res);
			return;
		}
		String series = "";
		String token = "";
		try {

			int delim = header.indexOf(":");

			if (delim != -1) {
				series = header.substring(0, delim);
				token = header.substring(delim + 1);
			}else{
				throw new BadCredentialsException("Wrong header contents");
			}

			if (debug) {
				logger.debug("Token Authentication Authorization header found for series '"
						+ series + "'");
			}

			if (authenticationIsRequired(series)) {
				SeriesTokenAuthenticationToken authRequest = new SeriesTokenAuthenticationToken(
						series, token, null);
				authRequest.setDetails(authenticationDetailsSource
						.buildDetails(request));

				Authentication authResult;

				authResult = authenticationManager.authenticate(authRequest);

				if (debug) {
					logger.debug("Authentication success: "
							+ authResult.toString());
				}

				SecurityContextHolder.getContext()
						.setAuthentication(authResult);

				onSuccessfulAuthentication(request, response, authResult);
			}
		} catch (AuthenticationException failed) {
			// Authentication failed
			if (debug) {
				logger.debug("Authentication request for series: " + series
						+ " failed: " + failed.toString());
			}

			SecurityContextHolder.getContext().setAuthentication(null);

			onUnsuccessfulAuthentication(request, response, failed);
			if (ignoreFailure) {
				chain.doFilter(request, response);
			} else {
				authenticationEntryPoint.commence(request, response, failed);
			}

			return;
		}

		// Authentication success

		chain.doFilter(request, response);

	}

	private boolean authenticationIsRequired(String series) {
		Authentication existingAuth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (existingAuth == null || !existingAuth.isAuthenticated()) {
			return true;
		}
		if (existingAuth instanceof SeriesTokenAuthenticationToken
				&& !existingAuth.getName().equals(series)) {
			return true;
		}
		if (existingAuth instanceof AnonymousAuthenticationToken) {
			return true;
		}
		return false;
	}

	protected void onSuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, Authentication authResult)
			throws IOException {
		if(authenticationResultHandler != null)
			authenticationResultHandler.onAuthenticationSuccess(request, response, authResult);
	}

	protected void onUnsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException {
		if(authenticationResultHandler != null)
			authenticationResultHandler.onUnsuccessfulAuthentication(request, response, failed);
	}

	public void setAuthenticationResultHandler(
			AuthenticationResultHandler authenticationResultHandler) {
		this.authenticationResultHandler = authenticationResultHandler;
	}
}
