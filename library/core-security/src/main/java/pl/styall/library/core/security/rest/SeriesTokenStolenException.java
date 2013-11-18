package pl.styall.library.core.security.rest;

import org.springframework.security.core.AuthenticationException;


public class SeriesTokenStolenException extends AuthenticationException {

	public SeriesTokenStolenException(String msg) {
		super(msg);
	}
    public SeriesTokenStolenException(String msg, Throwable t) {
        super(msg, t);
    }

	private static final long serialVersionUID = 357529390269386912L;


}
