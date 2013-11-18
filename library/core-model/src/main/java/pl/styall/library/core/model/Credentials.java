package pl.styall.library.core.model;

import java.io.Serializable;
import java.security.SecureRandom;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.springframework.security.crypto.keygen.KeyGenerators;

@Embeddable
public class Credentials implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6193222478729895383L;

	@Transient
	private final static SecureRandom random = new SecureRandom();

	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String token;

	@Column(nullable = false)
	private String mail;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private Boolean active;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken() {
		this.token = KeyGenerators.string().generateKey();
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


}
