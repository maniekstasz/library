package pl.stalkon.social.model;

import java.util.List;
import java.util.Set;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.jpa.JpaTemplate;
import org.springframework.social.connect.jpa.RemoteUser;
import org.springframework.util.MultiValueMap;

public interface SocialUserService extends JpaTemplate, ConnectionSignUp {
	public String execute(Connection<?> connection);

	public Set<String> findUsersConnectedTo(
			String providerId,
			Set<String> providerUserIds);

	public List<RemoteUser> getPrimary(String userId, String providerId) ;

	public int getRank(String userId, String providerId) ;

	public List<RemoteUser> getAll(
			String userId,
			MultiValueMap<String, String> providerUsers) ;

	public List<RemoteUser> getAll(String userId);

	public List<RemoteUser> getAll(String userId, String providerId);

	public RemoteUser get(
			String userId,
			String providerId,
			String providerUserId);

	public List<RemoteUser> get(String providerId, String providerUserId)
			throws IncorrectResultSizeDataAccessException;

	public void remove(String userId, String providerId);

	public void remove(String userId, String providerId, String providerUserId);

	public RemoteUser createRemoteUser(
			String userId,
			String providerId,
			String providerUserId,
			int rank,
			String displayName,
			String profileUrl,
			String imageUrl,
			String accessToken,
			String secret,
			String refreshToken,
			Long expireTime);

	public RemoteUser save(RemoteUser user) ;
}
