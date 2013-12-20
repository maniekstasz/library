package pl.styall.library.core.security.ajax;

public interface LoginService {

  LoginStatus getStatus();

  LoginStatus login(String username, String password);
}
