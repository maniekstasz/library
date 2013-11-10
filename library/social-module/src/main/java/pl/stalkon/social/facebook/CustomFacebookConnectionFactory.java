package pl.stalkon.social.facebook;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookServiceProvider;

public class CustomFacebookConnectionFactory extends
		OAuth2ConnectionFactory<Facebook> {

	public CustomFacebookConnectionFactory(String clientId, String clientSecret) {
		super("facebook", new FacebookServiceProvider(clientId, clientSecret), new CustomFacebookAdapter());
	}
	public CustomFacebookConnectionFactory(String clientId, String clientSecret, String appNameSpace) {
		super("facebook", new FacebookServiceProvider(clientId, clientSecret), new CustomFacebookAdapter());
	}
}
