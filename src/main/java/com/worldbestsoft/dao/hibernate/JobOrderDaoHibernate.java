package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.model.JobOrder;
import com.worldbestsoft.model.criteria.JobOrderCriteria;

@Repository("jobOrderDao")
public class JobOrderDaoHibernate extends GenericDaoHibernate<JobOrder, Long> implements JobOrderDao {

	public JobOrderDaoHibernate() {
		super(JobOrder.class);
	}

	/* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.JobOrderDao#query(com.worldbestsoft.model.criteria.JobOrderCriteria, int, int, java.lang.String, java.lang.String)
	 */
	@Override
    public List<JobOrder> query(JobOrderCriteria criteria, final int page, final int pageSize, final String sortColumn, final String order) {
		String hsql = "select o from JobOrder o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (null != criteria) {
			if (null != criteria.getEmployee() && null != criteria.getEmployee().getId()) {
				hsql += " and o.employee.id = :employeeId";
				params.put("employeeId", criteria.getEmployee().getId());
			}
			if (null != criteria.getSaleOrderItem() && null != criteria.getSaleOrderItem().getSaleOrder() && StringUtils.isNotBlank(criteria.getSaleOrderItem().getSaleOrder().getSaleOrderNo())) {
				hsql += " and o.saleOrderItem.saleOrder.saleOrderNo = :saleOrderNo";
				params.put("saleOrderNo", criteria.getSaleOrderItem().getSaleOrder().getSaleOrderNo());
			}
			if (null != criteria.getSaleOrderItem() && null != criteria.getSaleOrderItem().getCatalog() && StringUtils.isNotBlank(criteria.getSaleOrderItem().getCatalog().getCode())) {
				hsql += " and o.saleOrderItem.catalog = :catalogCode";
				params.put("catalogCode", criteria.getSaleOrderItem().getCatalog().getCode());
			}
			
			if (null != criteria.getStartDateFrom()) {
				hsql += " and o.startDate >= :startDateFrom";
				params.put("startDateFrom", criteria.getStartDateFrom());
			}
			if (null != criteria.getStartDateTo()) {
				hsql += " and o.startDate <= :startDateTo";
				params.put("startDateTo", criteria.getStartDateTo());
			}
			
			if (null != criteria.getEndDateFrom()) {
				hsql += " and o.endDate >= :endDateFrom";
				params.put("endDateFrom", criteria.getEndDateFrom());
			}
			if (null != criteria.getStartDateTo()) {
				hsql += " and o.endDate <= :endDateTo";
				params.put("endDateTo", criteria.getEndDateTo());
			}
			if (StringUtils.isNotBlank(criteria.getStatus())) {
				hsql += " and o.status = :status";
				params.put("status", criteria.getStatus());
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
	 * @see com.worldbestsoft.dao.hibernate.JobOrderDao#querySize(com.worldbestsoft.model.criteria.JobOrderCriteria)
	 */
	@Override
    public Integer querySize(JobOrderCriteria criteria) {
		String hsql = "select count(*) from JobOrder o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (null != criteria) {
			if (null != criteria.getEmployee() && null != criteria.getEmployee().getId()) {
				hsql += " and o.employee.id = :employeeId";
				params.put("employeeId", criteria.getEmployee().getId());
			}
			if (null != criteria.getSaleOrderItem() && null != criteria.getSaleOrderItem().getSaleOrder() && StringUtils.isNotBlank(criteria.getSaleOrderItem().getSaleOrder().getSaleOrderNo())) {
				hsql += " and o.saleOrderItem.saleOrder.saleOrderNo = :saleOrderNo";
				params.put("saleOrderNo", criteria.getSaleOrderItem().getSaleOrder().getSaleOrderNo());
			}
			if (null != criteria.getSaleOrderItem() && null != criteria.getSaleOrderItem().getCatalog() && StringUtils.isNotBlank(criteria.getSaleOrderItem().getCatalog().getCode())) {
				hsql += " and o.saleOrderItem.catalog = :catalogCode";
				params.put("catalogCode", criteria.getSaleOrderItem().getCatalog().getCode());
			}
			
			if (null != criteria.getStartDateFrom()) {
				hsql += " and o.startDate >= :startDateFrom";
				params.put("startDateFrom", criteria.getStartDateFrom());
			}
			if (null != criteria.getStartDateTo()) {
				hsql += " and o.startDate <= :startDateTo";
				params.put("startDateTo", criteria.getStartDateTo());
			}
			
			if (null != criteria.getEndDateFrom()) {
				hsql += " and o.endDate >= :endDateFrom";
				params.put("endDateFrom", criteria.getEndDateFrom());
			}
			if (null != criteria.getStartDateTo()) {
				hsql += " and o.endDate <= :endDateTo";
				params.put("endDateTo", criteria.getEndDateTo());
			}
			if (StringUtils.isNotBlank(criteria.getStatus())) {
				hsql += " and o.status = :status";
				params.put("status", criteria.getStatus());
			}
			
		}
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		return ((Number) queryObj.uniqueResult()).intValue();
	}

}
