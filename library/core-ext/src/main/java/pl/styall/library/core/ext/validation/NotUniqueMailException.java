package pl.styall.library.core.ext.validation;

public class NotUniqueMailException extends Exception {
	public String objectPath;

	public NotUniqueMailException(String objectPath) {
		this.objectPath = objectPath;
	}
}
