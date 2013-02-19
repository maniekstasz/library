package pl.stalkon.social.singleconnection.interfaces;


public interface SocialUserService {
	RemoteUser get(String providerId, String providerUserId);
	RemoteUser get(Long userId);
	RemoteUser get(Long userId, String providerId, String providerUserId);

	RemoteUser save(RemoteUser remoteUser);

	boolean isUserIdConnectedTo(String providerId, Long userId);

	RemoteUser createRemoteUser(Long userId, String providerId,
			String providerUserId, String displayName, String profileUrl,
			String accessToken, String secret, String refreshToken,
			Long expireTime);
}
