package pl.styall.library.core.ext;

import org.apache.commons.lang3.StringUtils;



public class UrlHelper {

	
	public static String getUrlSafeString(String str){
		String abbr = StringUtils.abbreviate(str, 70);
		String withoutAccent = StringUtils.stripAccents(abbr);
		String replacedOhter = withoutAccent.replaceAll("[\\W]","-");
		return replacedOhter;
	}
}
