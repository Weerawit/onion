package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.InvItemDao;
import com.worldbestsoft.model.InvItem;

@Repository("invItemDao")
public class InvItemDaoHibernate extends GenericDaoHibernate<InvItem, Long> implements InvItemDao {

	public InvItemDaoHibernate() {
	    super(InvItem.class);
    }
	
    @Override
    public List<InvItem> query(InvItem criteria, final int page, final int pageSize, final String sortColumn, final String order) {
		String hsql = "select o from InvItem o where 1=1 ";
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
			if (null != criteria.getInvItemGroup() && StringUtils.isNotEmpty(criteria.getInvItemGroup().getCode())) {
				hsql += " and o.invItemGroup.code = :invItemGroupCode";
				params.put("invItemGroupCode", criteria.getInvItemGroup().getCode());
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
	public Integer querySize(InvItem criteria) {
		String hsql = "select count(*) from InvItem o where 1=1 ";
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
			if (null != criteria.getInvItemGroup() && StringUtils.isNotEmpty(criteria.getInvItemGroup().getCode())) {
				hsql += " and o.invItemGroup.code = :invItemGroupCode";
				params.put("invItemGroupCode", criteria.getInvItemGroup().getCode());
			}
		}
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		return ((Number) queryObj.uniqueResult()).intValue();
	}

	
	@Override
    public List<InvItem> checkDuplicate(String code, Long id) {
		String hsql = "select o from InvItem o where 1=1 and o.code = :code";
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
	
	
	@Override
    public InvItem findByInvItemCode(String invItemCode) {
		String hsql = "select o from InvItem o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		hsql += " and o.code = :code";
		params.put("code", invItemCode);
		hsql += " order by o.code";
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		List<InvItem> result = queryObj.list();
		if (null != result && result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	@Override
    public InvItem save(InvItem object) {
		Session session = getSession();
		Long id = (Long) session.save(object);
		return get(id);
	}

	
}
