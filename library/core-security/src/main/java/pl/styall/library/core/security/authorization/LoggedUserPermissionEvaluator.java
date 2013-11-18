package pl.styall.library.core.security.authorization;

import java.security.Principal;

import org.springframework.security.core.Authentication;

import pl.styall.library.core.security.authentication.LoggedUser;


public class LoggedUserPermissionEvaluator<T extends LoggedUser> {

	public boolean isLoggedUserId(Long userId, Principal principal){
		if(principal == null || userId == null){
			return false;
		}
		LoggedUser socialLoggedUser = (LoggedUser) ((Authentication) principal)
				.getPrincipal();
		return userId.equals(socialLoggedUser.getId());
	}

}
