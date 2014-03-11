package pl.styall.library.core.ext;

import org.apache.commons.lang3.StringUtils;



public class UrlHelper {

	
	public static String getUrlSafeString(String str){
		if(str == null) return null;
		String abbr = StringUtils.abbreviate(str, 70);
		String withoutAccent = StringUtils.stripAccents(abbr.toLowerCase().replaceAll("ł", "l"));
		String replacedOhter = withoutAccent.replaceAll("[\\W]","-");
		return replacedOhter;
	}
}
