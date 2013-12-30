package pl.styall.library.core.security.authentication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {
	
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

//	public AuthenticationProvider(UserDetailsService userService, PasswordEncoder passwordEncoder) {
//		this.userService = userService;
//		this.passwordEncoder = passwordEncoder;
//	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {

		String login = (String) authentication.getPrincipal();
		LoggedUser user = (LoggedUser) userDetailsService.loadUserByUsername(login);

		authenticationChecks(user, authentication);

		if(user instanceof CredentialsContainer){
			user.eraseCredentials();
		}
		
		return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
	}

	private void authenticationChecks(LoggedUser user,
			Authentication authentication) throws AuthenticationException {
		String requestPassword = (String) authentication.getCredentials();
		
		if(passwordEncoder.matches(requestPassword, user.getPassword())){
			throw new BadCredentialsException("Bad credentials");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
