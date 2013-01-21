package pl.styall.library.core.security.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import pl.styall.library.core.security.authentication.LoggedUser;

@Service("springLoginService")
public class SpringSecurityLoginService implements LoginService {

    @Autowired
    @Qualifier("authenticationManager")
    AuthenticationManager authenticationManager;

    public LoginStatus getStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null){
        	return new LoginStatus(false, null, null);
        }

        if (!auth.getName().equals("anonymousUser") && auth.isAuthenticated() && !(auth.getClass().isAssignableFrom(String.class))) {
            LoggedUser user = (LoggedUser) auth.getPrincipal();
            return new LoginStatus(true, user.getUsername(), user.getId());
        } else {
            return new LoginStatus(false, null, null);
        }
    }

    public LoginStatus login(String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        try {
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            LoggedUser user = (LoggedUser) auth.getPrincipal();
            return new LoginStatus(auth.isAuthenticated(), user.getUsername(), user.getId());
        } catch (BadCredentialsException e) {
            return new LoginStatus(false, null, null);
        }
    }
}
