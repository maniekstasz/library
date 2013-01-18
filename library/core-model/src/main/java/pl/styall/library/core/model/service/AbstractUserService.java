package pl.styall.library.core.model.service;

import pl.styall.library.core.form.UserRegForm;
import pl.styall.library.core.model.AbstractAddress;
import pl.styall.library.core.model.AbstractUser;
import pl.styall.library.core.model.AbstractUserData;




public interface AbstractUserService<USER extends AbstractUser<?,?>> {
	
	public boolean chechMailExists(String mail);
	public boolean changePassword(Long id, String oldPassword, String newPassword);
//	public void addAddress(Long userId, Address address);
}
