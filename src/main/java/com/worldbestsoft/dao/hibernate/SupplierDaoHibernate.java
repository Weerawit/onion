package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.SupplierDao;
import com.worldbestsoft.model.Supplier;

@Repository("supplierDao")
public class SupplierDaoHibernate extends GenericDaoHibernate<Supplier, Long> implements SupplierDao {

	public SupplierDaoHibernate() {
	    super(Supplier.class);
    }
	
    /* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.SupplierDao#query(com.worldbestsoft.model.Supplier, int, int, java.lang.String, java.lang.String)
	 */
    @Override
    public List<Supplier> query(Supplier criteria, final int page, final int pageSize, final String sortColumn, final String order) {
		String hsql = "select o from Supplier o where 1=1 ";
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
			if (StringUtils.isNotEmpty(criteria.getContactName())) {
				hsql += " and o.contactName like :contactName";
				params.put("contactName", criteria.getContactName() + "%");
			}
			if (StringUtils.isNotEmpty(criteria.getContactTel())) {
				hsql += " and o.contactTel like :contactTel";
				params.put("contactTel", criteria.getContactTel() + "%");
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
	 * @see com.worldbestsoft.dao.hibernate.SupplierDao#querySize(com.worldbestsoft.model.Supplier)
	 */
	@Override
    public Integer querySize(Supplier criteria) {
		String hsql = "select count(*) from Supplier o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(criteria.getCode())) {
			hsql += " and o.code like :code";
			params.put("code", criteria.getCode() + "%");
		}
		if (StringUtils.isNotEmpty(criteria.getName())) {
			hsql += " and o.name like :name";
			params.put("name", criteria.getName() + "%");
		}
		if (StringUtils.isNotEmpty(criteria.getContactName())) {
			hsql += " and o.contactName like :contactName";
			params.put("contactName", criteria.getContactName() + "%");
		}
		if (StringUtils.isNotEmpty(criteria.getContactTel())) {
			hsql += " and o.contactTel like :contactTel";
			params.put("contactTel", criteria.getContactTel() + "%");
		}
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		return ((Number) queryObj.uniqueResult()).intValue();
	}

	
    /* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.SupplierDao#checkDuplicate(java.lang.String, java.lang.Long)
	 */
    @Override
    public List<Supplier> checkDuplicate(String code, Long id) {
		String hsql = "select o from Supplier o where 1=1 and o.code = :code";
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
	 * @see com.worldbestsoft.dao.hibernate.SupplierDao#findBySupplierCode(java.lang.String)
	 */
    @Override
    public Supplier findBySupplierCode(String supplierCode) {
		String hsql = "select o from Supplier o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		hsql += " and o.code = :code";
		params.put("code", supplierCode);
		hsql += " order by o.code";
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		List<Supplier> result = queryObj.list();
		if (null != result && result.size() > 0) {
			return result.get(0);
		}
		return null;
	}


}
