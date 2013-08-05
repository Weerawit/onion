package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.InvStockDao;
import com.worldbestsoft.model.InvStock;

@Repository("invStockDao")
public class InvStockDaoHibernate extends GenericDaoHibernate<InvStock, Long> implements InvStockDao {

	public InvStockDaoHibernate() {
	    super(InvStock.class);
    }
	
    @Override
    public List<InvStock> query(InvStock criteria, final int page, final int pageSize, final String sortColumn, final String order) {
		String hsql = "select o from InvStock o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (null != criteria) {
			if (null != criteria.getInvItem() && StringUtils.isNotEmpty(criteria.getInvItem().getCode())) {
				hsql += " and o.invItem.code like :invItemCode";
				params.put("invItemCode", criteria.getInvItem().getCode());
			}
		}
		if (StringUtils.isNotBlank(sortColumn)) {
			hsql += " order by o." + sortColumn;
			if (StringUtils.isNotEmpty(order)) {
				hsql += "1".equals(order) ? " ASC" : " DESC";
			}
		}
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		return queryObj.setFirstResult(page * pageSize).setMaxResults(pageSize).list();
	}
	
	@Override
    public Integer querySize(InvStock criteria) {
		String hsql = "select count(*) from InvStock o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (null != criteria) {
			if (null != criteria.getInvItem() && StringUtils.isNotEmpty(criteria.getInvItem().getCode())) {
				hsql += " and o.invItem.code like :invItemCode";
				params.put("invItemCode", criteria.getInvItem().getCode());
			}
		}
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		return ((Number) queryObj.uniqueResult()).intValue();
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
