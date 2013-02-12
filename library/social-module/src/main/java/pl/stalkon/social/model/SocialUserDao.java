package pl.stalkon.social.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.social.connect.jpa.RemoteUser;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;


@SuppressWarnings("unchecked")
@Repository
public class SocialUserDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Set<String> findUsersConnectedTo(String providerId,
			Set<String> providerUserIds) {
		String hql = "select distinct userId from SocialUser su where su.providerId = :providerId and su.providerUserId in (:providerUserIds)";
		return new HashSet<String>(currentSession().createQuery(hql)
				.setString("providerId", providerId)
				.setParameterList("providerUserId", providerUserIds).list());
	}

	public List<RemoteUser> getAll(String userId) {
		return currentSession().createCriteria(SocialUser.class)
				.add(Restrictions.eq("userId", userId))
				.addOrder(Order.desc("providerId"))
				.addOrder(Order.desc("rank")).list();
	}

	public List<RemoteUser> getAll(String userId, String providerId) {
		return currentSession().createCriteria(SocialUser.class)
				.add(Restrictions.eq("userId", userId))
				.add(Restrictions.eq("providerId", providerId))
				.addOrder(Order.desc("rank")).list();
	}

	public List<RemoteUser> getAll(String userId,
			MultiValueMap<String, String> providerUsers) {
		if (providerUsers.isEmpty())
			return Collections.<RemoteUser> emptyList();

		Criteria c = currentSession().createCriteria(SocialUser.class).add(
				Restrictions.eq("userId", userId));

		Disjunction orExp = Restrictions.disjunction();
		for (Iterator<Entry<String, List<String>>> it = providerUsers
				.entrySet().iterator(); it.hasNext();) {
			Entry<String, List<String>> entry = it.next();
			String providerId = entry.getKey();
			LogicalExpression andExp = Restrictions.and(
					Restrictions.eq("providerId", providerId),
					Restrictions.in("providerUserId", entry.getValue()));
			orExp.add(andExp);
		}
		c.add(orExp);
		return c.list();
	}

	public RemoteUser get(String userId, String providerId,
			String providerUserId) {
		return (RemoteUser) currentSession().createCriteria(SocialUser.class)
				.add(Restrictions.eq("userId", userId))
				.add(Restrictions.eq("providerId", providerId))
				.add(Restrictions.eq("providerUserId", providerUserId))
				.uniqueResult();
	}

	public List<RemoteUser> get(String providerId, String providerUserId)
			throws IncorrectResultSizeDataAccessException {
		return currentSession().createCriteria(SocialUser.class)
				.add(Restrictions.eq("providerId", providerId))
				.add(Restrictions.eq("providerUserId", providerUserId)).list();
	}

	public void remove(String userId, String providerId) {
		currentSession()
				.createQuery(
						"DELETE FROM SocialUser where providerId = :providerId and userId = :userId")
				.setString("providerId", providerId)
				.setString("userId", userId).executeUpdate();
	}

	public void remove(String userId, String providerId, String providerUserId) {
		currentSession()
				.createQuery(
						"DELETE FROM SocialUser where providerId = :providerId and userId = :userId and providerUserId = :providerUserId")
				.setString("providerId", providerId)
				.setString("userId", userId)
				.setString("providerUserId", providerUserId).executeUpdate();
	}

	public List<RemoteUser> getPrimary(String userId, String providerId) {
		return currentSession().createCriteria(SocialUser.class)
				.add(Restrictions.eq("userId", userId))
				.add(Restrictions.eq("providerId", providerId))
				.add(Restrictions.eq("rank", 1)).list();
	}

	public int getRank(String userId, String providerId) {
		String hql = "select max(rank) as rank from SocialUser su where userId = :userId and su.providerId = :providerId";
		List<Integer> result = currentSession().createQuery(hql)
				.setString("userId", userId)
				.setString("providerId", providerId).list();
		if (result.isEmpty() || result.get(0) == null)
			return 1;
		return result.get(0) + 1;
	}

	public RemoteUser createRemoteUser(String userId, String providerId,
			String providerUserId, int rank, String displayName,
			String profileUrl, String imageUrl, String accessToken,
			String secret, String refreshToken, Long expireTime) {
		RemoteUser ru = get(userId, providerId, providerUserId);
		if (ru != null)
			return ru;
		SocialUser su = new SocialUser(userId, providerId, providerUserId, rank,
				displayName, profileUrl, imageUrl, accessToken, secret,
				refreshToken, expireTime);
		return save(su);
	}

	public RemoteUser save(RemoteUser user) {
		currentSession().saveOrUpdate((SocialUser) user);
		return user;
	}

	protected Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

}
