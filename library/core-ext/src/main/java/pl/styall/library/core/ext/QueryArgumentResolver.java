package pl.styall.library.core.ext;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

public class QueryArgumentResolver implements WebArgumentResolver {

	@Autowired
	private ObjectMapper jacksonObjectMapper;
	@Override
	public Object resolveArgument(MethodParameter param,
			NativeWebRequest request) throws Exception {

		Annotation[] paramAnns = param.getParameterAnnotations();
		Class paramType = param.getParameterType();
		for (Annotation paramAnn : paramAnns) {
			if (QueryObject.class.isInstance(paramAnn)) {
				QueryObject queryObject = (QueryObject) paramAnn;
				boolean required = queryObject.required();
				HttpServletRequest httprequest = (HttpServletRequest) request
						.getNativeRequest();
				String url = httprequest.getRequestURL().toString();
				String queryString = httprequest.getQueryString();
				Object result = jacksonObjectMapper.readValue(getParsedObject(url, queryString), paramType);
				if (result == null && required)
					throw new IllegalStateException("Missing parameter");
				return result;
			}
		}
		return WebArgumentResolver.UNRESOLVED;
	}

	private String getParsedObject(String url, String queryString) {
		StringBuffer result = new StringBuffer();
		result.append("{");
		String urlParts[] = url.split("/");
		for (int i = 0; i < urlParts.length; i++) {
			if (urlParts[i].matches("^[0-9].*$")) {
				result.append('"' + urlParts[i - 1] + "Id" + '"').append(":").append(urlParts[i]).append(",");
			}
		}
		if (queryString != null) {
			String queryParts[] = queryString.split("&");
			for (String part : queryParts) {
				final String par[] = part.split("=");
				if(!par[1].matches("(^-?(([0-9]+)|([0-9]+[\\.\\,][0-9]+))$)|(\bfalse\b|\btrue\b|\bnull\b)"))
					result.append('"' + par[0] + '"').append(":").append('"' + par[1] + '"').append(",");
				else
					result.append('"' + par[0] + '"').append(":").append(par[1]).append(",");
			}
		}
		int a = result.lastIndexOf(",");
		if(a != -1){
			result.deleteCharAt(a);
		}
		result.append("}");
		return result.toString();
	}

}
