package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.SaleOrderPaymentSummaryReportDao;
import com.worldbestsoft.model.ConstantModel;
import com.worldbestsoft.model.SaleOrder;
import com.worldbestsoft.model.criteria.SaleOrderCriteria;
import com.worldbestsoft.model.criteria.SaleOrderPaymentSummaryReport;

@Repository("saleOrderPaymentSummaryReportDao")
public class SaleOrderPaymentSummaryReportDaoHibernate extends GenericDaoHibernate<SaleOrder, Long> implements SaleOrderPaymentSummaryReportDao {

	public SaleOrderPaymentSummaryReportDaoHibernate() {
		super(SaleOrder.class);
	}

    /* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.SaleOrderPaymentSummaryReportDao#query(com.worldbestsoft.model.criteria.SaleOrderCriteria, int, int, java.lang.String, java.lang.String)
	 */
    @Override
    public List<SaleOrderPaymentSummaryReport> query(SaleOrderCriteria criteria, final int page, final int pageSize, final String sortColumn, final String order) {
    		String sql = "select c.name as customerName, d.document_no as saleOrderNo, (o.total_price - o.payment_paid) as nonPaidAmount from sale_order o ";
    		sql += "left join document_number d on o.document_number_internal_no = d.internal_no ";
    		sql += "left join customer c on o.customer_id = c.id where 1=1 ";
    		final Map<String, Object> params = new HashMap<String, Object>();
    		if (null != criteria) {
    			if (null != criteria.getDocumentNumber() && StringUtils.isNotEmpty(criteria.getDocumentNumber().getDocumentNo())) {
    				sql += " and d.document_no like :saleOrderNo";
    				params.put("saleOrderNo", criteria.getDocumentNumber().getDocumentNo() + "%");
    			}
    			if (null != criteria.getCustomer() && StringUtils.isNotEmpty(criteria.getCustomer().getName())) {
    				sql += " and c.name = :customerName";
    				params.put("customerName", criteria.getCustomer().getName());
    			}
    			if (null != criteria.getDeliveryDateFrom()) {
    				sql += " and o.delivery_date >= :deliveryDateFrom";
    				params.put("deliveryDateFrom", criteria.getDeliveryDateFrom());
    			}
    			if (null != criteria.getDeliveryDateTo()) {
    				sql += " and o.delivery_date <= :deliveryDateTo";
    				params.put("deliveryDateTo", criteria.getDeliveryDateTo());
    			}
    		}
		sql += " and o.status <> :status";
		params.put("status", ConstantModel.SaleOrderStatus.CANCEL.getCode());
		sql += " and (o.payment_status <> :paymentStatus or o.payment_status is null)";
		params.put("paymentStatus", ConstantModel.PaymentStatus.FULLY_PAID.getCode());
    		if (StringUtils.isNotBlank(sortColumn)) {
    			sql += " order by " + sortColumn;
    			if (StringUtils.isNotEmpty(order)) {
    				sql += "1".equals(order) ? " ASC" : " DESC";
    			}
    		}
    		
    		SQLQuery sqlQueryObj = getSession().createSQLQuery(sql);
    		sqlQueryObj.setProperties(params);
    		sqlQueryObj.setResultTransformer(Transformers.aliasToBean(SaleOrderPaymentSummaryReport.class));
    		return sqlQueryObj.setFirstResult(page * pageSize).setMaxResults(pageSize).list();
	}


    /* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.SaleOrderPaymentSummaryReportDao#querySize(com.worldbestsoft.model.criteria.SaleOrderCriteria)
	 */
    @Override
    public Integer querySize(SaleOrderCriteria criteria) {
    		String sql = "select count(*) from ("; 
    		sql += "select c.name as customerName, d.document_no as saleOrderNo, (o.total_price - o.payment_paid) as nonPaidAmount from sale_order o  ";
    		sql += "left join document_number d on o.document_number_internal_no = d.internal_no ";
    		sql += "left join customer c on o.customer_id = c.id where 1=1 ";
    		final Map<String, Object> params = new HashMap<String, Object>();
    		if (null != criteria) {
    			if (null != criteria.getDocumentNumber() && StringUtils.isNotEmpty(criteria.getDocumentNumber().getDocumentNo())) {
    				sql += " and d.document_no like :saleOrderNo";
    				params.put("saleOrderNo", criteria.getDocumentNumber().getDocumentNo() + "%");
    			}
    			if (null != criteria.getCustomer() && StringUtils.isNotEmpty(criteria.getCustomer().getName())) {
    				sql += " and c.name = :customerName";
    				params.put("customerName", criteria.getCustomer().getName());
    			}
    			if (null != criteria.getDeliveryDateFrom()) {
    				sql += " and o.delivery_date >= :deliveryDateFrom";
    				params.put("deliveryDateFrom", criteria.getDeliveryDateFrom());
    			}
    			if (null != criteria.getDeliveryDateTo()) {
    				sql += " and o.delivery_date <= :deliveryDateTo";
    				params.put("deliveryDateTo", criteria.getDeliveryDateTo());
    			}
    		}
    		sql += " and o.status <> :status";
    		params.put("status", ConstantModel.SaleOrderStatus.CANCEL.getCode());
    		sql += " and (o.payment_status <> :paymentStatus or o.payment_status is null)";
    		params.put("paymentStatus", ConstantModel.PaymentStatus.FULLY_PAID.getCode());
		sql += ") a";
		
		SQLQuery sqlQueryObj = getSession().createSQLQuery(sql);
		sqlQueryObj.setProperties(params);
		return ((Number) sqlQueryObj.uniqueResult()).intValue();
	}
	
}
