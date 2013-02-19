package pl.stalkon.social.singleconnection.interfaces;

import org.springframework.social.connect.Connection;


public interface UsersConnectionRepository {

	Long findUserIdByConnection(Connection<?> connection);
	boolean isUserIdConnectedTo(String providerId, Long userId);
	ConnectionRepository createConnectionRepository(Long userId);
}
