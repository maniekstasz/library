package pl.styall.library.core.model.dao;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

public class CriteriaConfigurer {

	public void configureCriteria(Criteria criteria, Map<String, Object> params){
		if(params.containsKey("orderby")){
			if(params.containsKey("order")){
				if(((String)params.get("order")).equals("desc")){
					criteria.addOrder(Order.desc((String)params.get("orderby")));
				}else{
					criteria.addOrder(Order.desc((String)params.get("orderby")));
				}
			}else{
				criteria.addOrder(Order.desc((String)params.get("orderby")));
			}
		}
	}

}
