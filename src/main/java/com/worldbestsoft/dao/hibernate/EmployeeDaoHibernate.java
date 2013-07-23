package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.EmployeeDao;
import com.worldbestsoft.model.Employee;

@Repository("employeeDao")
public class EmployeeDaoHibernate extends GenericDaoHibernate<Employee, Long> implements EmployeeDao {

	public EmployeeDaoHibernate() {
	    super(Employee.class);
    }
	
    /* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.EmployeeDao#query(com.worldbestsoft.model.Employee, int, int, java.lang.String, java.lang.String)
	 */
    @Override
    public List<Employee> query(Employee criteria, final int page, final int pageSize, final String sortColumn, final String order) {
		String hsql = "select o from Employee o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (null != criteria) {
			if (StringUtils.isNotEmpty(criteria.getFirstName())) {
				hsql += " and o.firstName like :firstName";
				params.put("firstName", criteria.getFirstName() + "%");
			}
			if (StringUtils.isNotEmpty(criteria.getLastName())) {
				hsql += " and o.lastName like :lastName";
				params.put("lastName", criteria.getLastName() + "%");
			}
			if (StringUtils.isNotEmpty(criteria.getNickName())) {
				hsql += " and o.nickName like :nickName";
				params.put("nickName", criteria.getNickName() + "%");
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
	 * @see com.worldbestsoft.dao.hibernate.EmployeeDao#querySize(com.worldbestsoft.model.Employee)
	 */
	@Override
    public Integer querySize(Employee criteria) {
		String hsql = "select count(*) from Employee o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (null != criteria) {
			if (StringUtils.isNotEmpty(criteria.getFirstName())) {
				hsql += " and o.firstName like :firstName";
				params.put("firstName", criteria.getFirstName() + "%");
			}
			if (StringUtils.isNotEmpty(criteria.getLastName())) {
				hsql += " and o.lastName like :lastName";
				params.put("lastName", criteria.getLastName() + "%");
			}
			if (StringUtils.isNotEmpty(criteria.getNickName())) {
				hsql += " and o.nickName like :nickName";
				params.put("nickName", criteria.getNickName() + "%");
			}
		}
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		return ((Number) queryObj.uniqueResult()).intValue();
	}

}
