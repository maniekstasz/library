package pl.stalkon.social.singleconnection.impl;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.social.connect.NotConnectedException;

import pl.stalkon.social.singleconnection.interfaces.AddConnectionHandler;
import pl.stalkon.social.singleconnection.interfaces.ConnectionRepository;
import pl.stalkon.social.singleconnection.interfaces.RemoteUser;
import pl.stalkon.social.singleconnection.interfaces.SocialUserService;

public class ConnectionRepositoryImpl implements ConnectionRepository {

	private final ConnectionFactoryLocator connectionFactoryLocator;

	private final TextEncryptor textEncryptor;

	private final Long userId;

	private final SocialUserService socialUserService;
	
	private AddConnectionHandler addConnectionHandler;

	public ConnectionRepositoryImpl(Long userId,
			SocialUserService socialUserService,
			ConnectionFactoryLocator connectionFactoryLocator,
			TextEncryptor textEncryptor) {
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.textEncryptor = textEncryptor;
		this.userId = userId;
		this.socialUserService = socialUserService;
	}

	@Override
	public void updateConnection(Connection<?> connection) {
		ConnectionData data = connection.createData();

		RemoteUser su = socialUserService.get(userId, data.getProviderId(),
				data.getProviderUserId());
		if (su != null) {
			su.setDisplayName(data.getDisplayName());
			su.setProfileUrl(data.getProfileUrl());
			su.setAccessToken(encrypt(data.getAccessToken()));
			su.setSecret(encrypt(data.getSecret()));
			su.setRefreshToken(encrypt(data.getRefreshToken()));
			su.setExpireTime(data.getExpireTime());
			su = socialUserService.save(su);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
		String providerId = getProviderId(apiType);
		Connection<A> connection = (Connection<A>) findPrimaryConnection(providerId);
		if (connection == null) {
			throw new NotConnectedException(providerId);
		}
		return connection;
	}

	private <A> String getProviderId(Class<A> apiType) {
		return connectionFactoryLocator.getConnectionFactory(apiType)
				.getProviderId();
	}

	private Connection<?> findPrimaryConnection(String providerId) {
		RemoteUser remoteUser = socialUserService.get(userId);
		Connection<?> connection = mapEntity(remoteUser);
		if (connection != null) {
			return connection;
		} else {
			return null;
		}		
	}

	@Override
	public void addConnection(Connection<?> connection) {
		try {
			ConnectionData data = connection.createData();
			socialUserService.createRemoteUser(userId, data.getProviderId(),
					data.getProviderUserId(), data.getDisplayName(),
					data.getProfileUrl(), encrypt(data.getAccessToken()),
					encrypt(data.getSecret()), encrypt(data.getRefreshToken()),
					data.getExpireTime());
			
			if(addConnectionHandler != null){
				addConnectionHandler.afterAddConnection(connection);
			}
		} catch (DuplicateKeyException e) {
			throw new DuplicateConnectionException(connection.getKey());
		}

	}

	private Connection<?> mapEntity(RemoteUser socialUser) {
		ConnectionData connectionData = mapConnectionData(socialUser);
		ConnectionFactory<?> connectionFactory = connectionFactoryLocator
				.getConnectionFactory(connectionData.getProviderId());
		return connectionFactory.createConnection(connectionData);
	}

	private ConnectionData mapConnectionData(RemoteUser socialUser) {
		
		//TODO: check if userprofileurl is needed
		return new ConnectionData(socialUser.getProviderId(),
				socialUser.getProviderUserId(), socialUser.getDisplayName(),
				socialUser.getProfileUrl(), "",
				decrypt(socialUser.getAccessToken()),
				decrypt(socialUser.getSecret()),
				decrypt(socialUser.getRefreshToken()),
				expireTime(socialUser.getExpireTime()));
	}

	private String decrypt(String encryptedText) {
		return encryptedText != null ? textEncryptor.decrypt(encryptedText)
				: encryptedText;
	}

	private Long expireTime(Long expireTime) {
		return expireTime == null || expireTime == 0 ? null : expireTime;
	}

	private String encrypt(String text) {
		return text != null ? textEncryptor.encrypt(text) : text;
	}

	public void setAddConnectionHandler(AddConnectionHandler addConnectionHandler) {
		this.addConnectionHandler = addConnectionHandler;
	}

}
