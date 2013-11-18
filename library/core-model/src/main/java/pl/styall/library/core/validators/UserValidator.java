package pl.styall.library.core.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pl.styall.library.core.form.UserFormModel;
import pl.styall.library.core.model.service.AbstractUserService;


public class UserValidator implements Validator {

	
	private AbstractUserService<?> userService;
	
	public UserValidator(AbstractUserService<?> userService) {
		this.userService = userService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return UserFormModel.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserFormModel user = (UserFormModel) target;
		if (userService.chechMailExists(user.getMail())) {
			errors.rejectValue("mail", "mail.exists", "Email already exists");
		}
		if (userService.chechUsernameExists(user.getUsername())) {
			errors.rejectValue("mail", "mail.exists", "Email already exists");
		}
	}


}
