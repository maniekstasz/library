package pl.styall.library.core.security.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationResultHandler {
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authResult) throws IOException;
	public void onUnsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed) throws IOException;
			
}
