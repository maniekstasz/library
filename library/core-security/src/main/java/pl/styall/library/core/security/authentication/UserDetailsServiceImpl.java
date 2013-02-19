package pl.styall.library.core.security.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.styall.library.core.model.AbstractUser;
import pl.styall.library.core.model.UserRole;
import pl.styall.library.core.model.dao.UserDao;

@Service("userDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDao userDao;

//	public UserDetailsServiceImpl(UserDao userDao) {
//		this.userDao = userDao;
//	}

	@Override
	public UserDetails loadUserByLogin(String login) {
		AbstractUser user = userDao.loadUserByLogin(login);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		List<String> roles = new ArrayList<String>();
		List<UserRole> userRoles = user.getUserRoles();
		for (UserRole ur : userRoles) {
			roles.add(ur.getRole());
		}
		return new LoggedUser(user.getId(), user.getCredentials().getUsername(), user.getCredentials()
				.getPassword(), user.getCredentials().getSalt(), user.getUserData().getImageUrl(),
				getAuthorities(roles));
	}

	protected Collection<SimpleGrantedAuthority> getAuthorities(
			List<String> userRoles) {
		List<SimpleGrantedAuthority> authList = new ArrayList<SimpleGrantedAuthority>();
		for (String ur : userRoles) {
			authList.add(new SimpleGrantedAuthority(ur));
		}
		return authList; // TODO Auto-generated method stub
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		return loadUserByLogin(username);
	}

}
