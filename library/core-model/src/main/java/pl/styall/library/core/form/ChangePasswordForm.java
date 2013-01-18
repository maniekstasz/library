package pl.styall.library.core.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import pl.styall.library.core.ext.validation.FieldMatch;


@FieldMatch.List({
    @FieldMatch(first = "newPassword", second = "confirmPassword", message = "The password fields must match"),
})
public class ChangePasswordForm {
	private String oldPassword;
	@NotNull
	@Size(min=6, max=20)
	@Pattern(regexp="^[0-9a-zA-Z,.-=;'!@#$%^&*()_,.]+$")
	private String newPassword;
	@NotNull
	private String confirmPassword;
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
