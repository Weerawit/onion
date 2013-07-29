package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.InvStockDao;
import com.worldbestsoft.model.InvStock;

@Repository("invStockDao")
public class InvStockDaoHibernate extends GenericDaoHibernate<InvStock, Long> implements InvStockDao {

	public InvStockDaoHibernate() {
	    super(InvStock.class);
    }
	
    /* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.InvStockDao#findByInvItemCode(java.lang.String)
	 */
    @Override
    public InvStock findByInvItemCode(String invItemCode) {
		String hsql = "select o from InvStock o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		hsql += " and o.invItem.code = :invItemCode";
		params.put("invItemCode", invItemCode);
		hsql += " order by o.invItem.code";
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		List<InvStock> result = queryObj.list();
		if (null != result && result.size() > 0) {
			return result.get(0);
		}
		return null;
	}


}
