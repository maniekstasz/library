package pl.stalkon.social.singleconnection;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.ServiceProvider;
import org.springframework.social.connect.Connection;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.util.Assert;

import pl.stalkon.social.singleconnection.interfaces.SocialUserDetailsService;
import pl.stalkon.social.singleconnection.interfaces.UsersConnectionRepository;


public class SocialAuthenticationProvider implements
		AuthenticationProvider {

	@Autowired
	private UsersConnectionRepository usersSingleConnectionRepository;

	@Autowired
	private SocialUserDetailsService userDetailsService;

	public SocialAuthenticationProvider(
			UsersConnectionRepository usersSingleConnectionRepository,
			SocialUserDetailsService userDetailsService) {
		super();
		this.usersSingleConnectionRepository = usersSingleConnectionRepository;
		this.userDetailsService = userDetailsService;
	}

	public boolean supports(Class<? extends Object> authentication) {
		return SocialAuthenticationToken.class.isAssignableFrom(authentication);
	}

	/**
	 * Authenticate user based on {@link SocialAuthenticationToken}
	 */
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		Assert.isInstanceOf(SocialAuthenticationToken.class, authentication,
				"unsupported authentication type");
		Assert.isTrue(!authentication.isAuthenticated(),
				"already authenticated");
		SocialAuthenticationToken authToken = (SocialAuthenticationToken) authentication;
		String providerId = authToken.getProviderId();
		Connection<?> connection = authToken.getConnection();
		Long userId = toUserId(connection);
		System.out.println("authenticationProvider");
		if (userId == null) {
			throw new BadCredentialsException("Unknown access token");
		}
		UserDetails userDetails = userDetailsService.loadUserByUserId(userId);
		if (userDetails == null) {
			throw new UsernameNotFoundException("Unknown connected account id");
		}
		return new SocialAuthenticationToken(connection, userDetails,
				authToken.getProviderAccountData(), getAuthorities(providerId,
						userDetails));
	}

	protected Long toUserId(Connection<?> connection) {
		Long userId = usersSingleConnectionRepository
				.findUserIdByConnection(connection);
		return userId;
	}

	/**
	 * Override to grant authorities based on {@link ServiceProvider} id and/or
	 * a user's account id
	 * 
	 * @param providerId
	 *            {@link ServiceProvider} id
	 * @param userDetails
	 *            {@link UserDetails} as returned by
	 *            {@link SocialUserDetailsService}
	 * @return extra authorities of this user not already returned by
	 *         {@link UserDetails#getAuthorities()}
	 */
	protected Collection<? extends GrantedAuthority> getAuthorities(
			String providerId, UserDetails userDetails) {
		return userDetails.getAuthorities();
	}

}
