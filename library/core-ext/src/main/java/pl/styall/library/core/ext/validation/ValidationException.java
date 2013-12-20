package pl.styall.library.core.ext.validation;

public class ValidationException extends Exception {
	public String objectPath;
	public String code;
	public ValidationException(String objectPath, String code) {
		this.objectPath = objectPath;
		this.code = code;
	}

	
}
