package pl.styall.library.core.model.service;

import pl.styall.library.core.model.AbstractUser;




public interface AbstractUserService<USER extends AbstractUser<?,?>> {
	
	public boolean chechMailExists(String mail);
	public boolean chechUsernameExists(String username);
	public USER get(Long id);
	public USER getInitialized(Long id);
	public boolean changePassword(Long id, String oldPassword, String newPassword);
	public USER getUserByMailOrUsername(String mailOrUsername);
	public USER add(USER user);
	public boolean activate(String token);
//	public void addAddress(Long userId, Address address);
}
