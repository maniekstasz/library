package pl.styall.library.core.security.rest;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SeriesTokenAuthenticationProvider implements AuthenticationProvider{
	
	private TokenService tokenService;
	private UserDetailsService userDetailsService;
	
	public SeriesTokenAuthenticationProvider(TokenService tokenService,
			UserDetailsService userDetailsService) {
		this.tokenService = tokenService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		SeriesTokenAuthenticationToken auth = (SeriesTokenAuthenticationToken) authentication;
		String series = (String) auth.getPrincipal();
		String password = (String) auth.getCredentials();
		UserAuthToken userAuthtoken = tokenService.getTokenForSeries(series);
		
		if(userAuthtoken == null){
			throw new BadCredentialsException("No such series: " + series);
		}else if(!password.equals(userAuthtoken.getTokenValue())){
			throw new SeriesTokenStolenException("Bad credentials");
		}
		
		
		
		UserDetails user = userDetailsService.loadUserByUsername(userAuthtoken.getLogin());
		
		if(user == null){
			throw new UsernameNotFoundException("There is no such user:" + userAuthtoken.getLogin());
		}
		
		if(user instanceof CredentialsContainer){
			((CredentialsContainer)user).eraseCredentials();
		}
		userAuthtoken.clearTokenValue();		
		return new SeriesTokenAuthenticationToken(user, userAuthtoken, user.getAuthorities(), userAuthtoken);
	}

	
	@Override
	public boolean supports(Class<?> authentication) {
		return SeriesTokenAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
