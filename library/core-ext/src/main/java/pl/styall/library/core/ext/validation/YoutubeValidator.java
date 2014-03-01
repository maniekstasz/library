package pl.styall.library.core.ext.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class YoutubeValidator implements
		ConstraintValidator<YoutubeValidData, String> {

	@Override
	public void initialize(YoutubeValidData constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value != null) {
			if (value.startsWith("http://youtu.be/")
					|| value.startsWith("http://www.youtube.com/watch?v=")) {
				return true;
			}
			return false;
		}
		return true;
	}

}
