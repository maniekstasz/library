package pl.stalkon.social.ext;

import pl.styall.library.core.model.AbstractUser;


public interface SocialUserDataFetcher<A> {
	AbstractUser fetchData(A api);
}
