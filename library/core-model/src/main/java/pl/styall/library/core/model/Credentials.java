package pl.styall.library.core.model;

import java.io.Serializable;
import java.security.SecureRandom;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Email;



@Embeddable
public class Credentials implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6193222478729895383L;

	@Transient
	private final static SecureRandom random = new SecureRandom();
	
	@Column(nullable=false)
	private String password;
	@Column(nullable=false)
	private String salt;
	@Column(nullable=false)
	private String token;
	
	@Column(nullable=false)
	private String mail;
	
	private String username;
	
	@Column(nullable=false)
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
	public String getSalt() {
		return salt;
	}

	public void setSalt() {
		this.salt = generateRandomString(32);
	}
	public String getToken() {
		return token;
	}

	public void setToken() {
		this.token = generateRandomString(32);
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
	
	private String generateRandomString(int length) {
		char[] values = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
				'8', '9' };
		String out = "";
		for (int i = 0; i < length; i++) {
			int idx = random.nextInt(values.length);
			out += values[idx];
		}

		return out;

	}



}
