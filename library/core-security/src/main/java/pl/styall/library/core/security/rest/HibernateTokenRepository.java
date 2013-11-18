package pl.styall.library.core.security.rest;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class HibernateTokenRepository implements TokenRepository {

	@Autowired
	private SessionFactory sessionFactory;

	public HibernateTokenRepository() {
	}

	@Override
	public void createNewToken(UserAuthToken token) {
		sessionFactory.getCurrentSession().save(token);
	}

	@Override
	public void updateToken(String series, String tokenValue, Date date) {
		UserAuthToken authToken = (UserAuthToken) sessionFactory.getCurrentSession().createQuery("from UserAuthToken where series = :series").setString("series", series).uniqueResult();
		authToken.setTokenValue(tokenValue);
		authToken.setDate(date);
		sessionFactory.getCurrentSession().update(authToken);		
	}

	@Override
	public UserAuthToken getTokenForSeries(String series) {
		UserAuthToken authToken = (UserAuthToken) sessionFactory.getCurrentSession().createQuery("from UserAuthToken where series = :series").setString("series", series).uniqueResult();
		return authToken;
	}

	@Override
	public void removeUserTokens(String login) {
		sessionFactory.getCurrentSession().createQuery("delete UserAuthToken where login = :login").setString("login", login).uniqueResult();
	}


	
}
