package pl.stalkon.social.facebook;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookServiceProvider;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

public class CustomFacebookConnectionFactory extends
		OAuth2ConnectionFactory<Facebook> {

	public CustomFacebookConnectionFactory(String clientId, String clientSecret) {
		super("facebook", new FacebookServiceProvider(clientId, clientSecret, null), new CustomFacebookAdapter());
	}
	public CustomFacebookConnectionFactory(String clientId, String clientSecret, String appNameSpace) {
		super("facebook", new FacebookServiceProvider(clientId, clientSecret, appNameSpace), new CustomFacebookAdapter());
	}
}
