package pl.stalkon.social.singleconnection.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;

import pl.stalkon.social.singleconnection.interfaces.AddConnectionHandler;
import pl.stalkon.social.singleconnection.interfaces.ConnectionRepository;
import pl.stalkon.social.singleconnection.interfaces.ConnectionSignup;
import pl.stalkon.social.singleconnection.interfaces.RemoteUser;
import pl.stalkon.social.singleconnection.interfaces.SocialUserService;
import pl.stalkon.social.singleconnection.interfaces.UsersConnectionRepository;


public class UsersConnectionRepositoryImpl implements UsersConnectionRepository {

	@Autowired
	private SocialUserService socialUserService;

	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;

	@Autowired
	private TextEncryptor textEncryptor;
	
	@Autowired(required=false)
	private AddConnectionHandler addConnectionHandler;

	@Autowired
	private ConnectionSignup connectionSignUp;
//	public UsersConnectionRepositoryImpl(
//			ConnectionFactoryLocator connectionFactoryLocator,
//			TextEncryptor textEncryptor) {
//		this.connectionFactoryLocator = connectionFactoryLocator;
//		this.textEncryptor = textEncryptor;
//	}



	@Override
	public Long findUserIdByConnection(Connection<?> connection) {
		ConnectionKey key = connection.getKey();
		RemoteUser remoteUser = socialUserService.get(key.getProviderId(),
				key.getProviderUserId());

		if (remoteUser != null) {
			return remoteUser.getUserId();
		}

		if (connectionSignUp != null) {
			Long newUserId = connectionSignUp.execute(connection);
			if (newUserId == null)
				// auto signup failed, so we need to go to a sign up form
				return null;
			createConnectionRepository(newUserId).addConnection(connection);
			return newUserId;
		}
		return null;
	}

	@Override
	public boolean isUserIdConnectedTo(String providerId, Long userId) {
		return socialUserService.isUserIdConnectedTo(providerId, userId);
	}

	@Override
	public ConnectionRepository createConnectionRepository(Long userId) {
		if (userId == null) {
			throw new IllegalArgumentException("userId cannot be null");
		}
		ConnectionRepositoryImpl repo = new ConnectionRepositoryImpl(userId, socialUserService,
				connectionFactoryLocator, textEncryptor);
		if(addConnectionHandler != null){
			repo.setAddConnectionHandler(addConnectionHandler);
		}
		return repo;
	}

	public void setSocialUserService(SocialUserService socialUserService) {
		this.socialUserService = socialUserService;
	}

	public void setConnectionSignUp(ConnectionSignup connectionSignUp) {
		this.connectionSignUp = connectionSignUp;
	}

	public void setAddConnectionHandler(AddConnectionHandler addConnectionHandler) {
		this.addConnectionHandler = addConnectionHandler;
	}



}
