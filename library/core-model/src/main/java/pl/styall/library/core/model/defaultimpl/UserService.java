package pl.styall.library.core.model.defaultimpl;

public interface UserService extends pl.styall.library.core.model.service.AbstractUserService<User> {
	public User register(UserRegForm userRegForm);
}
