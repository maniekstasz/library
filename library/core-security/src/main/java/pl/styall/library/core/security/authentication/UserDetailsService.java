package pl.styall.library.core.security.authentication;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {

	public UserDetails loadUserByLogin(String login);

}
