package pl.styall.library.core.security.rest;

import java.util.Date;
import java.util.List;



public interface TokenRepository {
	
    void createNewToken(UserAuthToken token);

    void updateToken(String series, String tokenValue, Date date);

    UserAuthToken getTokenForSeries(String series);

    void removeUserTokens(List<String> login);
    
}
