package pl.styall.library.core.security.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public LoginStatus getStatus() {
        return loginService.getStatus();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public LoginStatus login(@RequestParam("login") String login,
                             @RequestParam("password") String password) {

        return loginService.login(login, password);
    }
}
