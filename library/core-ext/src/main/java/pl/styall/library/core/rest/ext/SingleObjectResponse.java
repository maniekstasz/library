package pl.styall.library.core.rest.ext;

public class SingleObjectResponse {

	private final Object response;
	


	public SingleObjectResponse(Object response){
		this.response = response;
	}

	public Object getResponse() {
		return response;
	}
}
