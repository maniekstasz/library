package pl.stalkon.social.singleconnection.interfaces;


public interface SocialUserDetailsService {
	SocialUserDetails loadUserByUserId(Long userId);
}
