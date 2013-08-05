package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.InvGoodsMovementItemDao;
import com.worldbestsoft.model.InvGoodsMovementItem;

@Repository("invGoodsMovementItemDao")
public class InvGoodsMovementItemDaoHibernate extends GenericDaoHibernate<InvGoodsMovementItem, Long> implements InvGoodsMovementItemDao {

	public InvGoodsMovementItemDaoHibernate() {
	    super(InvGoodsMovementItem.class);
    }
	
    /* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.InvGoodsMovementItemDao#findByInvGoodMovement(java.lang.Long)
	 */
    @Override
    public List<InvGoodsMovementItem> findByInvGoodMovement(Long id) {
		String hsql = "select o from InvGoodsMovementItem o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		hsql += " and o.invGoodsMovement.id = :id";
		params.put("id", id);
		hsql += " order by o.id";
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		return queryObj.list();
	} 
	
}
