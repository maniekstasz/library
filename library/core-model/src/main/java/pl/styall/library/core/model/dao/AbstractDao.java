package pl.styall.library.core.model.dao;

import java.util.Map;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;

import pl.styall.library.core.model.CommonEntity;

@SuppressWarnings("unchecked")
public abstract class AbstractDao<EntityT extends CommonEntity>{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session currentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void add(EntityT object){
		currentSession().saveOrUpdate(object);
	}
	public void save(EntityT object){
		add(object);
	}
	public void update(EntityT object){
		currentSession().update(object);
	}
	public EntityT merge(Long id, EntityT from ){
		EntityT temp = (EntityT) currentSession().load(getType(), id);
		return (EntityT) currentSession().merge(from);
	}
	public EntityT get(Long id){
		return (EntityT) currentSession().load(getType(), id);
	}
	public EntityT getInitialized(Long id){
		EntityT object  = get(id);
		Hibernate.initialize(object);
		return object;
	}
	
	public void delete(Long id){
		EntityT object = (EntityT) currentSession().load(getType(), id);
		if(object != null){ // TODO wyrzucic wyjatek
			currentSession().delete(object);
		}
	}

	
	private Class<?> getType(){
		return GenericTypeResolver.resolveTypeArgument(getClass(), AbstractDao.class);
	}
}
