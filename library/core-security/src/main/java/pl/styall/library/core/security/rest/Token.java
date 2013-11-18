package pl.styall.library.core.security.rest;


public class Token {

	private final Object principal;
	private final String series;
	private final String token;
	
	public Token(String series, String token, Object principal) {
		super();
		this.series = series;
		this.token = token;
		this.principal = principal;
	}

	public String getSeries() {
		return series;
	}

	public String getToken() {
		return token;
	}

	public Object getPrincipal() {
		return principal;
	}
}
