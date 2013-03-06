package pl.styall.library.core.model.dao;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class CriteriaConfigurer {

	public void configureCriteria(Criteria criteria, Map<String, Object> params){
		System.out.println((Integer)params.get("pageCount"));
		if(params.containsKey("orderBy")){
			if(params.containsKey("order")){
				if(((String)params.get("order")).equals("desc")){
					criteria.addOrder(Order.desc((String)params.get("orderBy")));
				}else{
					criteria.addOrder(Order.asc((String)params.get("orderBy")));
				}
			}else{
				criteria.addOrder(Order.desc((String)params.get("orderBy")));
			}
		}
		if(params.containsKey("pageFrom") && params.containsKey("pageCount")){
			criteria.setFirstResult((Integer)params.get("pageFrom"));
			criteria.setMaxResults((Integer) params.get("pageCount"));
		}
//		if(params.containsKey("search")&&params.containsKey("searchBy")){
//			criteria.add(Restrictions.)
//		}
	}
	
	

}
