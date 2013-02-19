package pl.stalkon.social.singleconnection.interfaces;

import java.io.Serializable;

public interface RemoteUser extends Serializable {

	public Long getUserId();

	public String getProviderUserId();

	public void setProviderUserId(String provider);

	public String getProviderId();

	public void setProviderId(String providerId);

	public String getSecret();

	public void setSecret(String secret);

	public String getDisplayName();

	public void setDisplayName(String displayName);

	public String getProfileUrl();

	public void setProfileUrl(String profileUrl);

	public String getAccessToken();

	public void setAccessToken(String accessToken);

	public String getRefreshToken();

	public void setRefreshToken(String refreshToken);

	public Long getExpireTime();

	public void setExpireTime(Long expireTime);
}
