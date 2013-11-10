package pl.styall.library.core.security.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import pl.styall.library.core.security.authentication.LoggedUser;

public class UserMessageFilter extends GenericFilterBean {

	private long expires = 5*60*1000;

	private UserMessagesCountProvider userMessagesCountProvider;

	public UserMessageFilter(UserMessagesCountProvider userMessagesCountProvider) {
		super();
		this.userMessagesCountProvider = userMessagesCountProvider;
	}

	public void setUserMessagesCountProvider(
			UserMessagesCountProvider userMessagesCountProvider) {
		this.userMessagesCountProvider = userMessagesCountProvider;
	}

	private final String SESSION_ATTR_NAME = "userMessagesAttribute";

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();

		if (request.isUserInRole("ROLE_USER")) {
			if (session != null) {
				Object userMessagesObject = session
						.getAttribute(SESSION_ATTR_NAME);
				long currentTimestamp = new Date().getTime();
				boolean setAttribute = true;
				if (userMessagesObject != null) {
					UserMessageSessionAttribute userMessageSessionAttribute = (UserMessageSessionAttribute) userMessagesObject;
					long lastTimestamp = userMessageSessionAttribute
							.getTimestamp();
					if (!expired(lastTimestamp, currentTimestamp)) {
						setAttribute = false;
					}
				}
				if (setAttribute) {
					UserMessageSessionAttribute attr = getUserMessageSessionAttribute(currentTimestamp);
					session.setAttribute(SESSION_ATTR_NAME, attr);
				}
			}
		}
		chain.doFilter(req, res);
	}

	private UserMessageSessionAttribute getUserMessageSessionAttribute(
			long currentTimestamp) {
		LoggedUser loggedUser = (LoggedUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		int userMessagesCount = userMessagesCountProvider
				.getUserMessagesCount(loggedUser.getId());
		UserMessageSessionAttribute attr = new UserMessageSessionAttribute();
		attr.setUnhandledMessagesCount(userMessagesCount);
		attr.setTimestamp(currentTimestamp);
		return attr;
	}

	private boolean expired(long lastTimestamp, long currentTimestamp) {
		return currentTimestamp - lastTimestamp > expires;
	}

	/**
	 * Expiration time in seconds
	 * 
	 * @param expires
	 */
	public void setExpires(long expires) {
		this.expires = expires * 1000;
	}

}
