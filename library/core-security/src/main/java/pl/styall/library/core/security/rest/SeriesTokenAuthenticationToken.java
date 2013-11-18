package pl.styall.library.core.security.rest;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class SeriesTokenAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private final UserAuthToken userAuthToken;
	public SeriesTokenAuthenticationToken(Object principal, Object credentials,UserAuthToken userAuthToken) {
		super(principal, credentials);
		this.userAuthToken = userAuthToken;
	}
	
	public SeriesTokenAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities,UserAuthToken userAuthToken) {
		super(principal, credentials, authorities);
		this.userAuthToken = userAuthToken;
	}

	private static final long serialVersionUID = 3926622674177654551L;
	public UserAuthToken getUserAuthToken() {
		return userAuthToken;
	}

}
