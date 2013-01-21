package pl.styall.library.core.security.authentication;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoggedUser extends User  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 326479337951276717L;


	private UUID id;
	private String mail;
	private String salt;
	public LoggedUser(UUID id, String username, String mail, String password, String salt, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
		this.id = id;
		this.mail = mail;
		this.salt = salt;
	}

	public LoggedUser(UUID id, String username,String mail, String password,String salt,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = id;
		this.mail = mail;
		this.salt = salt;
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		salt = null;
	}
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}
