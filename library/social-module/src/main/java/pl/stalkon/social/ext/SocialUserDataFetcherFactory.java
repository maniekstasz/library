package pl.stalkon.social.ext;

import java.util.Map;

public class SocialUserDataFetcherFactory {

	private Map<String, SocialUserDataFetcher<?>> map;
	
	public SocialUserDataFetcherFactory(
			Map<String, SocialUserDataFetcher<?>> map) {
		this.map = map;
	}

	public SocialUserDataFetcher<?> getFetcher(String providerId){
		return map.get(providerId);
	}
}
