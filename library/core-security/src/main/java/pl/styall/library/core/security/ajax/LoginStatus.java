package pl.styall.library.core.security.ajax;

import java.util.UUID;

public class LoginStatus {

    private final boolean loggedIn;
    private final String username;
    private final String id;

    public LoginStatus(boolean loggedIn, String username, String id) {
        this.loggedIn = loggedIn;
        this.username = username;
        this.id = id;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getUsername() {
        return username;
    }

	public String getId() {
		return id;
	}
}