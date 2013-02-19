package pl.stalkon.social.singleconnection.interfaces;

import org.springframework.social.connect.Connection;

public interface AddConnectionHandler {
	void afterAddConnection(Connection<?> connection);
}
