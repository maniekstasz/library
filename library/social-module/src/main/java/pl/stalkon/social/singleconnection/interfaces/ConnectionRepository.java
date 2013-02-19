package pl.stalkon.social.singleconnection.interfaces;

import org.springframework.social.connect.Connection;

public interface ConnectionRepository {

	void updateConnection(Connection<?> connection);
	void addConnection(Connection<?> connection);
	<A> Connection<A> getPrimaryConnection(Class<A> apiType);
}
