package pl.styall.library.core.security.authentication;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoggedUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 326479337951276717L;

	private Long id;
	private String salt;
	private String imageUrl;


	public LoggedUser(Long id, String username, String password,
			String salt, String imageUrl,
			boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
		this.id = id;
		this.salt = salt;
		this.imageUrl = imageUrl;
	}

	public LoggedUser(Long id, String username, String password,
			String salt, String imageUrl,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = id;
		this.salt = salt;
		this.imageUrl = imageUrl;
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		salt = null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
