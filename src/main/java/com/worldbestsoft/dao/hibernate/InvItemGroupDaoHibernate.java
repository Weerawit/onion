package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.InvItemGroupDao;
import com.worldbestsoft.model.InvItemGroup;

@Repository("invItemGroupDao")
public class InvItemGroupDaoHibernate extends GenericDaoHibernate<InvItemGroup, Long> implements InvItemGroupDao {

	public InvItemGroupDaoHibernate() {
	    super(InvItemGroup.class);
    }
	
	/* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.ItemGroupDao#query(com.worldbestsoft.model.InvItemGroup)
	 */
    @Override
    public List<InvItemGroup> query(InvItemGroup criteria, final int page, final int pageSize, final String sortColumn, final String order) {
		String hsql = "select o from InvItemGroup o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (null != criteria) {
			if (StringUtils.isNotEmpty(criteria.getCode())) {
				hsql += " and o.code like :code";
				params.put("code", criteria.getCode() + "%");
			}
			if (StringUtils.isNotEmpty(criteria.getName())) {
				hsql += " and o.name like :name";
				params.put("name", criteria.getName() + "%");
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
	public Integer querySize(InvItemGroup criteria) {
		String hsql = "select count(*) from InvItemGroup o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (null != criteria) {
			if (StringUtils.isNotEmpty(criteria.getCode())) {
				hsql += " and o.code like :code";
				params.put("code", criteria.getCode() + "%");
			}
			if (StringUtils.isNotEmpty(criteria.getName())) {
				hsql += " and o.name like :name";
				params.put("name", criteria.getName() + "%");
			}
		}
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		return ((Number) queryObj.uniqueResult()).intValue();
	}

	
	/* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.ItemGroupDao#checkDuplicate(java.lang.String, java.lang.Long)
	 */
	@Override
    public List<InvItemGroup> checkDuplicate(String code, Long id) {
		String hsql = "select o from InvItemGroup o where 1=1 and o.code = :code";
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", code);
		if (null != id) {
			hsql += " and o.id <> :id";
			params.put("id", id);
		}
		hsql += " order by o.code";
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		return queryObj.list();
	}
	
	
	/* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.ItemGroupDao#findByInvItemGroupCode(java.lang.String)
	 */
	@Override
    public InvItemGroup findByInvItemGroupCode(String InvItemGroupCode) {
		String hsql = "select o from InvItemGroup o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		hsql += " and o.code = :code";
		params.put("code", InvItemGroupCode);
		hsql += " order by o.code";
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		List<InvItemGroup> result = queryObj.list();
		if (null != result && result.size() > 0) {
			return result.get(0);
		}
		return null;
	}


}
