package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.SaleOrderItemDao;
import com.worldbestsoft.model.SaleOrderItem;

@Repository("saleOrderItemDao")
public class SaleOrderItemDaoHibernate extends GenericDaoHibernate<SaleOrderItem, Long> implements SaleOrderItemDao {

	public SaleOrderItemDaoHibernate() {
	    super(SaleOrderItem.class);
    }
	
    /* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.SaleOrderItemDao#findBySaleOrder(java.lang.Long)
	 */
    @Override
    public List<SaleOrderItem> findBySaleOrder(Long id) {
		String hsql = "select o from SaleOrderItem o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		hsql += " and o.saleOrder.id = :id";
		params.put("id", id);
		hsql += " order by o.id";
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		return queryObj.list();
	} 


}
