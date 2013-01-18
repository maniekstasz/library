package pl.styall.library.core.ext.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

public class FieldMatchValidator implements
		ConstraintValidator<FieldMatch, Object> {
	private String firstFieldName;
	private String secondFieldName;

	@Override
	public void initialize(final FieldMatch constraintAnnotation) {
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
	}

	@Override
	public boolean isValid(final Object value,
			final ConstraintValidatorContext context) {
		boolean matches = false;
		try {
			final Object firstObj = BeanUtils
					.getProperty(value, firstFieldName);
			final Object secondObj = BeanUtils.getProperty(value,
					secondFieldName);
			boolean neitherSet = (firstObj == null) && (secondObj == null);
			if (neitherSet)
				return true;
			matches = firstObj != null && firstObj.equals(secondObj);
			if (!matches) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("message")
						.addNode(secondFieldName).addConstraintViolation();
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}

		return matches;
	}
}
