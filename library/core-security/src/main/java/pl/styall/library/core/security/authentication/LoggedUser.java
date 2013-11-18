package pl.styall.library.core.security.authentication;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoggedUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 326479337951276717L;

	private Long id;
	private String imageUrl;


	public LoggedUser(Long id, String username, String password,
			 String imageUrl,
			boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
		this.id = id;
		this.imageUrl = imageUrl;
	}

	public LoggedUser(Long id, String username, String password,
			 String imageUrl,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = id;
		this.imageUrl = imageUrl;
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
