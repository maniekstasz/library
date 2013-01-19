package pl.styall.library.core.model.defaultimpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.styall.library.core.model.Credentials;
import pl.styall.library.core.model.UserRole;

@Service("userService")
public class UserServiceImpl extends
		pl.styall.library.core.model.service.impl.AbstractUserServiceImpl<User>
		implements UserService {

	@Override
	@Transactional
	public User register(UserRegForm userRegForm) {
		User user = new User();
		Credentials credentials = new Credentials();
		String password = userRegForm.getPassword();
		Address address = userRegForm.getAddress();

		credentials.setSalt();
		credentials.setToken();
		credentials.setPassword(passwordEncoder.encodePassword(password,
				credentials.getSalt()));
		credentials.setMail(userRegForm.getMail());
		UserData userData = userRegForm.getUserData();
		user.setUserData(userData);
		userData.addAddress(address);
		UserRole userRole = userDao.loadUserRoleByName("ROLE_USER");
		if (userRole == null) {
			userRole = new UserRole();
			userRole.setRole("ROLE_USER");
		}
		user.addUserRole(userRole);
		user.setCredentials(credentials);
		userDao.save(user);
		return user;
	}

}
