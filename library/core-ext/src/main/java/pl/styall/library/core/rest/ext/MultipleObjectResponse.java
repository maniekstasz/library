package pl.styall.library.core.rest.ext;

import java.util.HashMap;
import java.util.Map;

public class MultipleObjectResponse {

	private Map<String, Object> response;

	public MultipleObjectResponse(int length) {
		this.response = new HashMap<String,Object>(length);
	}
	
	public MultipleObjectResponse() {
		this.response = new HashMap<String,Object>();
	}
	
	public void put(String key, Object val){
		response.put(key, val);
	}

	public Map<String, Object> getResponse() {
		return response;
	}
	
	

}
