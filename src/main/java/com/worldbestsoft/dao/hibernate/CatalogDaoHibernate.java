package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.CatalogDao;
import com.worldbestsoft.model.Catalog;

@Repository("catalogDao")
public class CatalogDaoHibernate extends GenericDaoHibernate<Catalog, Long> implements CatalogDao {

	public CatalogDaoHibernate() {
	    super(Catalog.class);
    }
	
    /* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.CatalogDao#query(com.worldbestsoft.model.Catalog, int, int, java.lang.String, java.lang.String)
	 */
    @Override
    public List<Catalog> query(Catalog criteria, final int page, final int pageSize, final String sortColumn, final String order) {
		String hsql = "select o from Catalog o where 1=1 ";
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
			if (null != criteria.getCatalogType() && StringUtils.isNotEmpty(criteria.getCatalogType().getCode())) {
				hsql += " and o.catalogType.code = :catalogTypeCode";
				params.put("catalogTypeCode", criteria.getCatalogType().getCode());
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
	
	/* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.CatalogDao#querySize(com.worldbestsoft.model.Catalog)
	 */
	@Override
    public Integer querySize(Catalog criteria) {
		String hsql = "select count(*) from Catalog o where 1=1 ";
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
			if (null != criteria.getCatalogType() && StringUtils.isNotEmpty(criteria.getCatalogType().getCode())) {
				hsql += " and o.catalogType.code = :catalogTypeCode";
				params.put("catalogTypeCode", criteria.getCatalogType().getCode());
			}
		}
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		return ((Number) queryObj.uniqueResult()).intValue();
	}

	
    /* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.CatalogDao#checkDuplicate(java.lang.String, java.lang.Long)
	 */
    @Override
    public List<Catalog> checkDuplicate(String code, Long id) {
		String hsql = "select o from Catalog o where 1=1 and o.code = :code";
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
	 * @see com.worldbestsoft.dao.hibernate.CatalogDao#findByCatalogCode(java.lang.String)
	 */
    @Override
    public Catalog findByCatalogCode(String catalogCode) {
		String hsql = "select o from Catalog o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		hsql += " and o.code = :code";
		params.put("code", catalogCode);
		hsql += " order by o.code";
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		List<Catalog> result = queryObj.list();
		if (null != result && result.size() > 0) {
			return result.get(0);
		}
		return null;
	}


}
