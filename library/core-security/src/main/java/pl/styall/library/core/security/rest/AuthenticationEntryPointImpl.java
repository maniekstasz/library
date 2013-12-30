package pl.styall.library.core.security.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		if (authException instanceof SeriesTokenStolenException) {
			response.setStatus(441);
		} else if (authException instanceof BadCredentialsException)
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		 else if (authException instanceof UsernameNotFoundException)
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		else {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}

	}

}
