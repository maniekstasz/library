package pl.styall.library.core.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import pl.styall.library.core.ext.validation.FieldMatch;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
})
public class UserFormModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1942093774561512549L;
	@NotNull
	@Size(min=6, max=20)
	@Pattern(regexp="^[0-9a-zA-Z,.-=;'!@#$%^&*()_]+$")
	private String password;
	
	@NotNull
	private String confirmPassword;
	
	@Email
	@NotEmpty
	private String mail;
	
	@NotNull
	@Pattern(regexp="^[0-9a-zA-Z_.]+$")
	@Size(min=5, max=20)
	private String username;
	
	public UserFormModel(){
		
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
