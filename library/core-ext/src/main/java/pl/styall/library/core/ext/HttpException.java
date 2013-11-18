package pl.styall.library.core.ext;

public abstract class HttpException extends Exception {

	private static final long serialVersionUID = 4197513942879451118L;

	public final int STATUS_CODE;
	
	public HttpException(int statusCode){
		this.STATUS_CODE = statusCode;
	}
}
