package pl.styall.library.core.model.dao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.validator.internal.util.ReflectionHelper;

public class DaoQueryObject {
	
	public enum CompareType{MORE,LESS,EQUAL, IN, IN_CONJUNCTION, IN_DISJUNCTION, LIKE}
	
	public String name;
	public Object value;
	public CompareType type;
	
	public DaoQueryObject(String name, Object value, CompareType type) {
		super();
		this.name = name;
		this.value = value;
		this.type = type;
	}
	
	public DaoQueryObject(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
		this.type = CompareType.EQUAL;
	}
	
	
	public boolean addCriteria(Criteria criteria, String alias){
		if(value == null){
			return false;
		}
//		if(!ReflectionHelper.containsDeclaredField(clazz, name))
//			return;

		alias = alias != ""? alias + ".": "";
		switch(type){
		case LESS:
			criteria.add(Restrictions.le(alias + name, value));
			break;
		case MORE:
			criteria.add(Restrictions.ge(alias + name, value));
			break;
		case IN:
			criteria.add(Restrictions.in(alias+name,(Collection) value));
			break;
		case IN_CONJUNCTION:
			Collection col = (Collection) value;
			if(col.size() == 0)
				return false;
			Conjunction conj = Restrictions.conjunction();
			for(Object ob: col){
				conj.add(Restrictions.eq(alias+name, ob));
			}
			criteria.add(conj);
			break;
		case IN_DISJUNCTION:
			Collection col2 = (Collection) value;
			if(col2.size() == 0)
				return false;
			Disjunction disj = Restrictions.disjunction();
			for(Object ob: col2){
				disj.add(Restrictions.eq(alias+name, ob));
			}
			criteria.add(disj);
			break;
		case LIKE:
			criteria.add(Restrictions.like(alias+name, value));
			break;
		default:
			criteria.add(Restrictions.eq(alias + name, value));
			break;
		}
		return true;
	}
	

}
