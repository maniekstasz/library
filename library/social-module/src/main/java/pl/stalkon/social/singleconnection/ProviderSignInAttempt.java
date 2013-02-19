package pl.stalkon.social.singleconnection;

import java.io.Serializable;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.DuplicateConnectionException;

import pl.stalkon.social.singleconnection.interfaces.UsersConnectionRepository;

/**
 * Models an attempt to sign-in to the application using a provider user identity.
 * Instances are created when the provider sign-in process could not be completed because no local user is associated with the provider user.
 * This could happen because the user has not yet signed up with the application, or has not yet connected their local application identity with the their provider identity.
 * For the former scenario, callers should invoke {@link #addConnection(String)} post-signup to establish a connection between a new user account and the provider account.
 * For the latter, existing users should sign-in using their local application credentials and formally connect to the provider they also wish to authenticate with.
 * @author Keith Donald
 */
@SuppressWarnings("serial")
public class ProviderSignInAttempt implements Serializable {

	/**
	 * Name of the session attribute ProviderSignInAttempt instances are indexed under.
	 */
	public static final String SESSION_ATTRIBUTE = ProviderSignInAttempt.class.getName();

	private final ConnectionData connectionData;

	private final ConnectionFactoryLocator connectionFactoryLocator;

	private final UsersConnectionRepository connectionRepository;

	public ProviderSignInAttempt(Connection<?> connection, ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository connectionRepository) {
		this.connectionData = connection.createData();
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.connectionRepository = connectionRepository;		
	}

	/**
	 * Get the connection to the provider user account the client attempted to sign-in as.
	 * Using this connection you may fetch a {@link Connection#fetchUserProfile() provider user profile} and use that to pre-populate a local user registration/signup form.
	 * You can also lookup the id of the provider and use that to display a provider-specific user-sign-in-attempt flash message e.g. "Your Facebook Account is not connected to a Local account. Please sign up."
	 */
	public Connection<?> getConnection() {
		return connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId()).createConnection(connectionData);
	}

	/**
	 * Connect the new local user to the provider.
	 * @throws DuplicateConnectionException if the user already has this connection
	 */
	void addConnection(Long userId) {
		connectionRepository.createConnectionRepository(userId).addConnection(getConnection());
	}

}