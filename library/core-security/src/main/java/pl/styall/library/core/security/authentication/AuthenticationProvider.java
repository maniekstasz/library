package pl.styall.library.core.security.authentication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.CredentialsContainer;


public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {
	
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;

//	public AuthenticationProvider(UserDetailsService userService, PasswordEncoder passwordEncoder) {
//		this.userService = userService;
//		this.passwordEncoder = passwordEncoder;
//	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {

		String login = (String) authentication.getPrincipal();
		LoggedUser user = (LoggedUser) userDetailsService.loadUserByLogin(login);

		authenticationChecks(user, authentication);

		if(user instanceof CredentialsContainer){
			user.eraseCredentials();
		}
		
		return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
	}

	private void authenticationChecks(LoggedUser user,
			Authentication authentication) throws AuthenticationException {
		String requestPassword = (String) authentication.getCredentials();
		String encodedPassword = passwordEncoder.encodePassword(requestPassword, user.getSalt());
		if(!encodedPassword.equals(user.getPassword())){
			throw new BadCredentialsException("Bad credentials");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
