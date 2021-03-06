package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.JobOrderSummaryReportDao;
import com.worldbestsoft.model.Employee;
import com.worldbestsoft.model.JobOrder;
import com.worldbestsoft.model.criteria.JobOrderCriteria;
import com.worldbestsoft.model.criteria.JobOrderSummaryReport;

@Repository("jobOrderSummaryReportDao")
public class JobOrderSummaryReportDaoHibernate extends GenericDaoHibernate<JobOrder, Long> implements JobOrderSummaryReportDao {

	public JobOrderSummaryReportDaoHibernate() {
		super(JobOrder.class);
	}

    /* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.JobOrderSummaryReportDao#query(com.worldbestsoft.model.criteria.JobOrderCriteria, int, int, java.lang.String, java.lang.String)
	 */
    @Override
    public List<JobOrderSummaryReport> query(JobOrderCriteria criteria, final int page, final int pageSize, final String sortColumn, final String order) {
    		String sql = "select m.first_name as firstName, m.last_name as lastName, o.status, sum(o.cost) as cost from job_order o ";
    		sql += "left join employee m on o.employee_id = m.id where 1=1 ";
    		final Map<String, Object> params = new HashMap<String, Object>();
    		if (null != criteria) {
    			if (null != criteria.getEmployee() && null != criteria.getEmployee().getId()) {
    				sql += " and o.employee_id = :employeeId";
    				params.put("employeeId", criteria.getEmployee().getId());
    			}
    			if (null != criteria.getStartDateFrom()) {
    				sql += " and o.start_date >= :startDateFrom";
    				params.put("startDateFrom", criteria.getStartDateFrom());
    			}
    			if (null != criteria.getStartDateTo()) {
    				sql += " and o.start_date <= :startDateTo";
    				params.put("startDateTo", criteria.getStartDateTo());
    			}
    			
    			if (null != criteria.getTargetEndDateFrom()) {
    				sql += " and o.target_end_date >= :targetEndDateFrom";
    				params.put("targetEndDateFrom", criteria.getTargetEndDateFrom());
    			}
    			if (null != criteria.getTargetEndDateTo()) {
    				sql += " and o.target_end_date <= :targetEndDateTo";
    				params.put("targetEndDateTo", criteria.getTargetEndDateTo());
    			}
    			if (StringUtils.isNotBlank(criteria.getStatus())) {
    				sql += " and o.status = :status";
    				params.put("status", criteria.getStatus());
    			}
    		}
    		sql += " group by o.employee_id, o.status";
    		if (StringUtils.isNotBlank(sortColumn)) {
    			sql += " order by " + sortColumn;
    			if (StringUtils.isNotEmpty(order)) {
    				sql += "1".equals(order) ? " ASC" : " DESC";
    			}
    		}
    		
    		SQLQuery sqlQueryObj = getSession().createSQLQuery(sql);
    		sqlQueryObj.setProperties(params);
    		sqlQueryObj.setResultTransformer(Transformers.aliasToBean(JobOrderSummaryReport.class));
    		return sqlQueryObj.setFirstResult(page * pageSize).setMaxResults(pageSize).list();
	}

    /* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.JobOrderSummaryReportDao#querySize(com.worldbestsoft.model.criteria.JobOrderCriteria)
	 */
    @Override
    public Integer querySize(JobOrderCriteria criteria) {
    		String sql = "select count(*) from ( ";
    		sql += "select m.first_name as firstName, m.last_name as lastName, o.status, sum(o.cost) as cost from job_order o ";
		sql += "left join employee m on o.employee_id = m.id where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (null != criteria) {
			if (null != criteria.getEmployee() && null != criteria.getEmployee().getId()) {
				sql += " and o.employee_id = :employeeId";
				params.put("employeeId", criteria.getEmployee().getId());
			}
			if (null != criteria.getStartDateFrom()) {
				sql += " and o.start_date >= :startDateFrom";
				params.put("startDateFrom", criteria.getStartDateFrom());
			}
			if (null != criteria.getStartDateTo()) {
				sql += " and o.start_date <= :startDateTo";
				params.put("startDateTo", criteria.getStartDateTo());
			}
			
			if (null != criteria.getTargetEndDateFrom()) {
				sql += " and o.target_end_date >= :targetEndDateFrom";
				params.put("targetEndDateFrom", criteria.getTargetEndDateFrom());
			}
			if (null != criteria.getTargetEndDateTo()) {
				sql += " and o.target_end_date <= :targetEndDateTo";
				params.put("targetEndDateTo", criteria.getTargetEndDateTo());
			}
			if (StringUtils.isNotBlank(criteria.getStatus())) {
				sql += " and o.status = :status";
				params.put("status", criteria.getStatus());
			}
		}
		sql += " group by o.employee_id, o.status";
		sql += ") a";
		
		SQLQuery sqlQueryObj = getSession().createSQLQuery(sql);
		sqlQueryObj.setProperties(params);
		return ((Number) sqlQueryObj.uniqueResult()).intValue();
	}
	
}
