package pl.styall.library.core.security.rest;

import java.util.Map;

public interface UserStatusMapper {

	public Map<String, Object> map(Object principal);

}
