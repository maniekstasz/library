package pl.styall.library.core.security.rest;


import java.util.Calendar;
import java.util.Date;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.transaction.annotation.Transactional;


public class TokenServiceImpl implements TokenService{

	private TokenRepository tokenRepository;
	private UserDetailsService userDetailsService;
	private BCryptPasswordEncoder passwordEncoder;
	private final static int TOKEN_EXPIRES_IN_MINUTES = 30;
	private int tokenExpires = TOKEN_EXPIRES_IN_MINUTES;
	
	public TokenServiceImpl(TokenRepository tokenRepository,
			UserDetailsService userDetailsService,
			BCryptPasswordEncoder passwordEncoder) {
		this.tokenRepository = tokenRepository;
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	
	@Override
	@Transactional
	public Token getToken(String login, String password) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(login);
		if(userDetails ==null){
			throw new BadCredentialsException("Wrong username or password");
		}
		if(passwordEncoder.matches(password, userDetails.getPassword())){
			UserAuthToken persistentToken = new UserAuthToken(login, KeyGenerators.string().generateKey(), generateToken(), new Date());
			tokenRepository.createNewToken(persistentToken);
			((CredentialsContainer)userDetails).eraseCredentials();
			return new Token(persistentToken.getSeries(), persistentToken.getTokenValue(), userDetails) ;
		}else{
			throw new BadCredentialsException("Wrong username or password");
		}
	}
	
	@Override
	public String generateToken(){
		byte[] securedKey = KeyGenerators.secureRandom(64).generateKey();
		String tokenValue = new String(Hex.encode(securedKey));
		return tokenValue;
	}


	@Override
	@Transactional
	public void createNewToken(UserAuthToken token) {
		tokenRepository.createNewToken(token);
		
	}


	@Override
	@Transactional
	public String updateToken(String series) {
		String tokenValue = generateToken();
		tokenRepository.updateToken(series, tokenValue, new Date());
		return tokenValue;
	}


	@Override
	@Transactional
	public UserAuthToken getTokenForSeries(String series) {
		return tokenRepository.getTokenForSeries(series);
	}


	@Override
	@Transactional
	public void removeUserTokens(String login) {
		tokenRepository.removeUserTokens(login);
	}


	@Override
	public boolean tokenExpired(UserAuthToken token) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, -tokenExpires);
		Date compareToDate = token.getDate();
		return compareToDate.before(cal.getTime());
	}

	public void setTokenExpires(int tokenExpires) {
		this.tokenExpires = tokenExpires;
	}

}
