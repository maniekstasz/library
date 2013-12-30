package pl.styall.library.core.security.rest;

public interface TokenService {
	Token getToken(String login, String password);
	String generateToken();
	void createNewToken(UserAuthToken token);

    String updateToken(String series);

    UserAuthToken getTokenForSeries(String series);

    void removeUserTokens(String login);
    
    boolean tokenExpired(UserAuthToken token);
}
