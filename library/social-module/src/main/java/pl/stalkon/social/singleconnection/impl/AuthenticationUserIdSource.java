package pl.stalkon.social.singleconnection.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import pl.stalkon.social.singleconnection.interfaces.SocialUserDetails;
import pl.stalkon.social.singleconnection.interfaces.UserIdSource;

public class AuthenticationUserIdSource implements UserIdSource {
	@Override
	public Long getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
		}
		SocialUserDetails userDetails = (SocialUserDetails) authentication.getPrincipal();
		return userDetails.getUserId();

	}
}
