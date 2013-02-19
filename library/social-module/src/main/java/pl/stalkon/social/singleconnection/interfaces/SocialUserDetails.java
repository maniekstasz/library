package pl.stalkon.social.singleconnection.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

public interface SocialUserDetails extends UserDetails {
	Long getUserId();
}
