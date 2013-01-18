package pl.styall.library.core.ext.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.styall.library.core.ext.validation.ValidationException;

@Controller
public abstract class BaseController {

	
	@Autowired
	private MessageSource messageSource;

	
//	@RequestMapping("/test")
//	public String test() {
//		return "test";
//	}
	
	@ExceptionHandler(ValidationException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Map<String, Object> handleNotUniqueMailException(
			ValidationException ex, HttpServletResponse response, Locale locale) {
		Map<String, Object> restResponse = new HashMap<String, Object>();
		List<ValidationError> validationErrors = new ArrayList<ValidationError>();
		validationErrors.add(new ValidationError(ex.objectPath, messageSource
				.getMessage(ex.code, null, locale)));
		restResponse.put("validationErrors", validationErrors);
		return restResponse;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Map<String, Object> handleException(
			MethodArgumentNotValidException ex, HttpServletResponse response,
			Locale locale) {
		Map<String, Object> restResponse = new HashMap<String, Object>();
		restResponse.put("errorMessage", "You cannot update nested objects. Nested object must be null");
		return restResponse;
	}

	@ExceptionHandler(NestedObjectNotNullException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Map<String, Object> handleNestedObjectNotNullException (
			ValidationException ex, HttpServletResponse response, Locale locale) {
		Map<String, Object> restResponse = new HashMap<String, Object>();
		List<ValidationError> validationErrors = new ArrayList<ValidationError>();
		validationErrors.add(new ValidationError(ex.objectPath, messageSource
				.getMessage(ex.code, null, locale)));
		restResponse.put("validationErrors", validationErrors);
		return restResponse;
	}
	
	private class ValidationError {
		public String field;
		public String message;

		public ValidationError(String field, String message) {
			this.field = field;
			this.message = message;
		}
	}

	protected boolean isAssociationsNull(Object object) throws NestedObjectNotNullException {
		Field fields[] = object.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(OneToMany.class)
					|| fields[i].isAnnotationPresent(ManyToMany.class)
					|| fields[i].isAnnotationPresent(OneToOne.class)
					|| fields[i].isAnnotationPresent(ManyToOne.class)) {
				if(fields[i] != null){
					throw new NestedObjectNotNullException();
				}
			}
		}
		return true;
	}
	
	private class NestedObjectNotNullException extends Exception{
		
	}

}
