package pl.styall.library.core.security.rest;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class UpdateTokenResultHandler implements AuthenticationResultHandler {

	private TokenService tokenService;
	
	public UpdateTokenResultHandler(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authResult)
			throws IOException {
		UserAuthToken userAuthToken = ((SeriesTokenAuthenticationToken) authResult).getUserAuthToken();
		if(tokenService.tokenExpired(userAuthToken)){
			String tokenValue = tokenService.updateToken(userAuthToken.getSeries());
			response.addHeader("AuthToken", userAuthToken.getSeries() + ":" + tokenValue);
		}
	}

	@Override
	public void onUnsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException {
		// TODO Auto-generated method stub
		
	}

	
	
}
