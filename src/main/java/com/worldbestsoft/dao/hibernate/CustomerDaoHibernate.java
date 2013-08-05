package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.CustomerDao;
import com.worldbestsoft.model.Customer;

@Repository("customerDao")
public class CustomerDaoHibernate extends GenericDaoHibernate<Customer, Long> implements CustomerDao {

	public CustomerDaoHibernate() {
	    super(Customer.class);
    }
	
    /* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.CustomerDao#query(com.worldbestsoft.model.Customer, int, int, java.lang.String, java.lang.String)
	 */
    @Override
    public List<Customer> query(Customer criteria, final int page, final int pageSize, final String sortColumn, final String order) {
		String hsql = "select o from Customer o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (null != criteria) {
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
			if (StringUtils.isNotEmpty(criteria.getCustomerType())) {
				hsql += " and o.customerType = :customerType";
				params.put("customerType", criteria.getCustomerType());
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
	 * @see com.worldbestsoft.dao.hibernate.CustomerDao#querySize(com.worldbestsoft.model.Customer)
	 */
	@Override
    public Integer querySize(Customer criteria) {
		String hsql = "select count(*) from Customer o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (null != criteria) {
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
			if (StringUtils.isNotEmpty(criteria.getCustomerType())) {
				hsql += " and o.customerType = :customerType";
				params.put("customerType", criteria.getCustomerType());
			}
		}
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		return ((Number) queryObj.uniqueResult()).intValue();
	}

}
