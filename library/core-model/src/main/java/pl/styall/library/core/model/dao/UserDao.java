package pl.styall.library.core.model.dao;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import pl.styall.library.core.model.AbstractUser;
import pl.styall.library.core.model.UserRole;



public class UserDao<USER extends AbstractUser<?, ?>> extends AbstractDao<USER> {
	@SuppressWarnings("unchecked")
	public boolean chechMailExists(String mail) {
		Collection<Long> id = currentSession()
				.createQuery(
						"Select id from User as user where user.credentials.mail=:mail")
				.setString("mail", mail).list();
		if (id.size() > 0) {
			return true;
		}
		return false;
	}
	
	
	@SuppressWarnings("unchecked")
	public boolean chechUsernameExists(String username) {
		Collection<Long> id = currentSession()
				.createQuery(
						"Select id from User as user where user.credentials.username=:username")
				.setString("username", username).list();
		if (id.size() > 0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public USER loadUserByLogin(String login) {
		USER user = (USER) currentSession()
				.createQuery(
						"from User as user where user.credentials.mail =:login or user.credentials.username =:login and user.credentials.active = true")
				.setString("login", login).uniqueResult();
		return user;
	}

	public UserRole loadUserRoleByName(String name) {
		return (UserRole) currentSession()
				.createQuery("from UserRole where role = :name")
				.setString("name", name).uniqueResult();
	}
	public USER activate(String token){
		USER user = (USER) currentSession().createQuery("from User as user where user.credentials.token =:token").setString("token", token).uniqueResult();
		return user;
	}
}
