package pl.styall.library.core.security.rest;

import java.io.Reader;
import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

	@Autowired
	private TokenService tokenService;

	private UserStatusMapper userMapper;
	
	
	public LoginController(TokenService tokenService,UserStatusMapper userMapper) {
		super();
		this.tokenService = tokenService;
		this.userMapper = userMapper;
	}

//	@RequestMapping(value = "/user/login/status", method = RequestMethod.GET)
//	@ResponseBody
//	public Map<String, Object> getStatus(Principal principal) {
//		UserDetails userDetails = (UserDetails) ((Authentication) principal)
//				.getPrincipal();
//		return userMapper.map(userDetails);
//	}

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	@ResponseBody
	public Token login(@RequestParam("login") String login,
			@RequestParam("password") String password, @RequestParam(value="deleteTokens", required=false)Boolean deleteTokens){
		if(deleteTokens != null){
			tokenService.removeUserTokens(login);
		}
		Token token =  tokenService.getToken(login, password);

		if(userMapper  != null){
			return new Token(token.getSeries(), token.getToken(), userMapper.map(token.getPrincipal()));
		}
		return token;
	}
}
