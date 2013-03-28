package pl.styall.library.core.model.dao;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class CriteriaConfigurer {

	public void configureCriteria(Criteria criteria, Order order, Integer first, Integer last){
		if(order != null){
			criteria.addOrder(order);
		}
		if(first != null){
			criteria.setFirstResult(first);
		}
		if(last != null){
			criteria.setMaxResults(last);
		}
//		if(params.containsKey("search")&&params.containsKey("searchBy")){
//			criteria.add(Restrictions.)
//		}
	}
	
	

}
