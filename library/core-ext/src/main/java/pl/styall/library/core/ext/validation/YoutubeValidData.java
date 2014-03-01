package pl.styall.library.core.ext.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = YoutubeValidator.class)
@Documented
public @interface YoutubeValidData {
	
	   	String message() default "{constraints.youtubeData}";

	    Class<?>[] groups() default {};

	    Class<? extends Payload>[] payload() default {};

}
