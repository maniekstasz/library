package pl.stalkon.social.facebook;

import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.connect.FacebookAdapter;

public class CustomFacebookAdapter extends FacebookAdapter {

	@Override
	public void setConnectionValues(Facebook facebook, ConnectionValues values) {
		FacebookProfile profile = facebook.userOperations().getUserProfile();
		values.setProviderUserId(profile.getId());
		values.setDisplayName(profile.getName());
		values.setProfileUrl("http://facebook.com/profile.php?id=" + profile.getId());
		values.setImageUrl("http://graph.facebook.com/" + profile.getId() + "/picture");
	}
}
