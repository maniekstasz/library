package pl.styall.library.core.model.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.styall.library.core.model.AbstractUser;
import pl.styall.library.core.model.dao.UserDao;
import pl.styall.library.core.model.defaultimpl.User;
import pl.styall.library.core.model.service.AbstractUserService;

public abstract class AbstractUserServiceImpl<USER extends AbstractUser<?, ?>>
		implements AbstractUserService<USER> {
	@Autowired
	protected UserDao<USER> userDao;

	@Autowired
	protected ShaPasswordEncoder passwordEncoder;


	@Override
	@Transactional
	public boolean chechMailExists(String mail) {
		return userDao.chechMailExists(mail);
	}

	@Override
	@Transactional
	public boolean changePassword(Long id, String oldPassword,
			String newPassword) {
		USER user = userDao.get(id);
		String encodedPassword = passwordEncoder.encodePassword(oldPassword,
				user.getCredentials().getSalt());
		if (encodedPassword.equals(user.getCredentials().getPassword())) {
			user.getCredentials().setPassword(encodedPassword);
			userDao.save(user);
		} else {
			return false;
		}
		return true;
	}
	
	@Override
	@Transactional
	public USER getUserByMailOrUsername(String mailOrUsername) {
		return userDao.loadUserByLogin(mailOrUsername);
	}
	
	@Override
	@Transactional
	public USER add(USER user){
		userDao.add(user);
		return user;
	}
	// @Override
	// public void addAddress(UUID userId, Address address) {
	// User user = userDao.get(userId);
	// user.addAddress(address);
	// userDao.save(user);
	// }

//	@Override
//	public USER verifyUser(String username, String password) {
//		USER user = userDao.loadUserByLogin(username);
//		if (user == null) {
//			return user;
//		}
//		String encPassword = passwordEncoder.encodePassword(password, user
//				.getCredentials().getSalt());
//		if (encPassword.equals(user.getCredentials().getPassword())) {
//			return user;
//		}
//		return null;
//	}
}
