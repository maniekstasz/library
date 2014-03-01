package pl.styall.library.core.ext;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

public class QueryArgumentResolver implements WebArgumentResolver {

	private final static String del = "&";

	@Override
	public Object resolveArgument(MethodParameter param,
			NativeWebRequest request) throws Exception {
		Annotation[] paramAnns = param.getParameterAnnotations();
		Class paramType = param.getParameterType();
		for (Annotation paramAnn : paramAnns) {
			if (QueryObject.class.isInstance(paramAnn)) {
				QueryObject queryObject = (QueryObject) paramAnn;
				boolean required = queryObject.required();
				String objects[] = queryObject.objects();
				HttpServletRequest httprequest = (HttpServletRequest) request
						.getNativeRequest();
				String url = httprequest.getRequestURL().toString();
				String queryString = httprequest.getQueryString();
				Map<String, Object> result = getParsedObject(url, queryString,
						objects);
				if (result == null && required)
					throw new IllegalStateException("Missing parameter");
				QueryObjectWrapper wrapper = new QueryObjectWrapper();
				wrapper.queryObject = result;
				return wrapper;
			}
		}
		return WebArgumentResolver.UNRESOLVED;
	}

	private boolean contains(String[] objects, String obj) {
		for (int i = 0; i < objects.length; i++) {
			if (objects[i].equals(obj)){
				return true;
			}
		}
		return false;
	}

	private Map<String, Object> getParsedObject(String url, String queryString,
			String[] objects) {
		Map<String, Object> result = new HashMap<String, Object>();
		String urlParts[] = url.split("/");
		
		boolean value = false;
		for (int i = 0; i < urlParts.length; i++) {
			if (value) {
				String params[] = urlParts[i].split(del);
				Map<String, Object> subObject = new HashMap<String, Object>();
				boolean wasId = false;
				for (int j = 0; j < params.length; j++) {
					if (params[j].matches("^[0-9]+$")) {
						if (!wasId) {
							subObject.put("id", urlParts[i]);
							wasId = true;
						}
					} else {
						String paramParts[] = params[j].split("=");
						if (paramParts.length == 2) {
							subObject.put(paramParts[0],
									paramParts[1]);
						}
					}
				}
				value = false;
				result.put(urlParts[i - 1], subObject);
			}else if (contains(objects, urlParts[i])) {
				value = true;
			}

		}
		if (queryString != null) {
			String queryParts[] = queryString.split(del);
			for (String part : queryParts) {
				final String par[] = part.split("=");
				if (par.length == 2){
					String coma[] = par[1].split(",");
					if(coma.length>1){
						List<Object> subObject = new ArrayList<Object>(coma.length);
						for(int i=0;i<coma.length;i++){
							subObject.add(coma[i]);
						}
						result.put(par[0], subObject);
					}else{
						result.put(par[0], par[1]);
					}

				}
			}
		}
		return result;
	}

//	private Object getByType(String part) {
//		if (part.matches("^-?[0-9]+$")) {
//			return new Integer(part);
//		} else if (part.matches("^-?[0-9]+[\\.\\,][0-9]+$")) {
//			return new Double(part);
//		} else if (part.matches("\bfalse\b|\btrue\b")) {
//			return new Boolean(part);
//		} else if (part.equals("null")) {
//			return null;
//		} else {
//			return part;
//		}
//	}

}
