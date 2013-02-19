package pl.stalkon.social.singleconnection.interfaces;

import org.springframework.social.connect.Connection;

public interface ConnectionSignup {
	Long execute(Connection<?> connection);
}
