package pl.stalkon.social.singleconnection;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.security.SocialAuthenticationRedirectException;

/**
 * @author Craig Walls
 */
class SocialAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private AuthenticationFailureHandler delegate;

	public SocialAuthenticationFailureHandler(AuthenticationFailureHandler delegate) {
		this.delegate = delegate;
	}
	
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		if (failed instanceof SocialAuthenticationRedirectException){
		  System.out.println(((SocialAuthenticationRedirectException)failed).getRedirectUrl());
		  response.sendRedirect(((SocialAuthenticationRedirectException)failed).getRedirectUrl()); 
		  return;
		}
		delegate.onAuthenticationFailure(request, response, failed);
	}

}