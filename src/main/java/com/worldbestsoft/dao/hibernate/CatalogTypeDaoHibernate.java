package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.CatalogTypeDao;
import com.worldbestsoft.model.CatalogType;

@Repository("catalogTypeDao")
public class CatalogTypeDaoHibernate extends GenericDaoHibernate<CatalogType, Long> implements CatalogTypeDao {

	public CatalogTypeDaoHibernate() {
	    super(CatalogType.class);
    }
	
	/* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.CatalogTypeDao#query(com.worldbestsoft.model.CatalogType)
	 */
    @Override
    public List<CatalogType> query(CatalogType criteria, final int page, final int pageSize, final String sortColumn, final String order) {
		String hsql = "select o from CatalogType o where 1=1 ";
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
	public Integer querySize(CatalogType criteria) {
		String hsql = "select count(*) from CatalogType o where 1=1 ";
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
	 * @see com.worldbestsoft.dao.hibernate.CatalogTypeDao#checkDuplicate(java.lang.String, java.lang.Long)
	 */
	@Override
    public List<CatalogType> checkDuplicate(String code, Long id) {
		String hsql = "select o from CatalogType o where 1=1 and o.code = :code";
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
	 * @see com.worldbestsoft.dao.hibernate.ItemGroupDao#findByCatalogTypeCode(java.lang.String)
	 */
	@Override
    public CatalogType findByCatalogTypeCode(String catalogTypeCode) {
		String hsql = "select o from CatalogType o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		hsql += " and o.code = :code";
		params.put("code", catalogTypeCode);
		hsql += " order by o.code";
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		List<CatalogType> result = queryObj.list();
		if (null != result && result.size() > 0) {
			return result.get(0);
		}
		return null;
	}


}
