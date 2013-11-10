package pl.styall.library.core.security.ajax;

public class LoginStatus {

    private final boolean loggedIn;
    private final String username;
    private final Long id;

    public LoginStatus(boolean loggedIn, String username, Long id) {
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

	public Long getId() {
		return id;
	}
}