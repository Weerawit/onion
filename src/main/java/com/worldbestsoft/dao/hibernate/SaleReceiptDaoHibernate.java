package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.SaleReceiptDao;
import com.worldbestsoft.model.ConstantModel;
import com.worldbestsoft.model.SaleReceipt;
import com.worldbestsoft.model.criteria.SaleReceiptCriteria;

@Repository("saleReceiptDao")
public class SaleReceiptDaoHibernate extends GenericDaoHibernate<SaleReceipt, Long> implements SaleReceiptDao {

	public SaleReceiptDaoHibernate() {
	    super(SaleReceipt.class);
    }
	
    /* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.SaleReceiptDao#query(com.worldbestsoft.model.criteria.SaleReceiptCriteria, int, int, java.lang.String, java.lang.String)
	 */
    @Override
    public List<SaleReceipt> query(SaleReceiptCriteria criteria, final int page, final int pageSize, final String sortColumn, final String order) {
		String hsql = "select o from SaleReceipt o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (null != criteria) {
			if (null != criteria.getReceiptDateFrom()) {
				hsql += " and o.receiptDate >= :receiptDateFrom";
				params.put("receiptDateFrom", criteria.getReceiptDateFrom());
			}
			if (null != criteria.getReceiptDateTo()) {
				hsql += " and o.receiptDate <= :receiptDateTo";
				params.put("receiptDateTo", criteria.getReceiptDateTo());
			}
			
			if (null != criteria.getSaleOrder()) {
				if (null != criteria.getSaleOrder().getDocumentNumber() && StringUtils.isNotBlank(criteria.getSaleOrder().getDocumentNumber().getDocumentNo())) {
					hsql += " and o.saleOrder.documentNumber.documentNo = :saleOrderNo";
					params.put("saleOrderNo", criteria.getSaleOrder().getDocumentNumber().getDocumentNo());
				}
				
				if (null != criteria.getSaleOrder().getCustomer() && StringUtils.isNotBlank(criteria.getSaleOrder().getCustomer().getName())) {
					hsql += " and o.saleOrder.customer.name = :customerName";
					params.put("customerName", criteria.getSaleOrder().getCustomer().getName());
				}
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
	 * @see com.worldbestsoft.dao.hibernate.SaleReceiptDao#querySize(com.worldbestsoft.model.criteria.SaleReceiptCriteria)
	 */
	@Override
    public Integer querySize(SaleReceiptCriteria criteria) {
		String hsql = "select count(*) from SaleReceipt o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (null != criteria) {
			if (null != criteria.getReceiptDateFrom()) {
				hsql += " and o.receiptDate >= :receiptDateFrom";
				params.put("receiptDateFrom", criteria.getReceiptDateFrom());
			}
			if (null != criteria.getReceiptDateTo()) {
				hsql += " and o.receiptDate <= :receiptDateTo";
				params.put("receiptDateTo", criteria.getReceiptDateTo());
			}
			
			if (null != criteria.getSaleOrder()) {
				if (null != criteria.getSaleOrder().getDocumentNumber() && StringUtils.isNotBlank(criteria.getSaleOrder().getDocumentNumber().getDocumentNo())) {
					hsql += " and o.saleOrder.documentNumber.documentNo = :saleOrderNo";
					params.put("saleOrderNo", criteria.getSaleOrder().getDocumentNumber().getDocumentNo());
				}
				
				if (null != criteria.getSaleOrder().getCustomer() && StringUtils.isNotBlank(criteria.getSaleOrder().getCustomer().getName())) {
					hsql += " and o.saleOrder.customer.name = :customerName";
					params.put("customerName", criteria.getSaleOrder().getCustomer().getName());
				}
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
	
	@Override
    public List<SaleReceipt> findBySaleOrderNo(String saleOrderNo) {
		String hsql = "select o from SaleReceipt o where 1=1 and o.status = :status' ";
		final Map<String, Object> params = new HashMap<String, Object>();
		hsql += " and o.saleOrder.documentNumber.documentNo = :saleOrderNo";
		params.put("saleOrderNo", saleOrderNo);
		params.put("status", ConstantModel.SaleReceiptStatus.ACTIVE.getCode());
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		return queryObj.list();
	}

}
