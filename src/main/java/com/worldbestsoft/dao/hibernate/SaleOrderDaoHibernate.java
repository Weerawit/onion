package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.SaleOrderDao;
import com.worldbestsoft.model.SaleOrder;
import com.worldbestsoft.model.criteria.SaleOrderCriteria;

@Repository("saleOrderDao")
public class SaleOrderDaoHibernate extends GenericDaoHibernate<SaleOrder, Long> implements SaleOrderDao {

	public SaleOrderDaoHibernate() {
	    super(SaleOrder.class);
    }
	
    /* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.saleOrderDao#query(com.worldbestsoft.model.SaleOrder, int, int, java.lang.String, java.lang.String)
	 */
    @Override
    public List<SaleOrder> query(SaleOrderCriteria criteria, final int page, final int pageSize, final String sortColumn, final String order) {
		String hsql = "select o from SaleOrder o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (null != criteria) {
			if (null != criteria.getDocumentNumber() && StringUtils.isNotEmpty(criteria.getDocumentNumber().getDocumentNo())) {
				hsql += " and o.documentNumber.documentNo like :saleOrderNo";
				params.put("saleOrderNo", criteria.getDocumentNumber().getDocumentNo() + "%");
			}
			if (null != criteria.getCustomer() && StringUtils.isNotEmpty(criteria.getCustomer().getName())) {
				hsql += " and o.customer.name = :customerName";
				params.put("customerName", criteria.getCustomer().getName());
			}
			if (null != criteria.getDeliveryDateFrom()) {
				hsql += " and o.deliveryDate >= :deliveryDateFrom";
				params.put("deliveryDateFrom", criteria.getDeliveryDateFrom());
			}
			if (null != criteria.getDeliveryDateTo()) {
				hsql += " and o.deliveryDate <= :deliveryDateTo";
				params.put("deliveryDateTo", criteria.getDeliveryDateTo());
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
	 * @see com.worldbestsoft.dao.hibernate.saleOrderDao#querySize(com.worldbestsoft.model.SaleOrder)
	 */
    @Override
    public Integer querySize(SaleOrderCriteria criteria) {
		String hsql = "select count(*) from SaleOrder o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (null != criteria) {
			if (null != criteria.getDocumentNumber() && StringUtils.isNotEmpty(criteria.getDocumentNumber().getDocumentNo())) {
				hsql += " and o.documentNumber.documentNo like :saleOrderNo";
				params.put("saleOrderNo", criteria.getDocumentNumber().getDocumentNo() + "%");
			}
			if (null != criteria.getCustomer() && StringUtils.isNotEmpty(criteria.getCustomer().getName())) {
				hsql += " and o.customer.name = :customerName";
				params.put("customerName", criteria.getCustomer().getName());
			}
			if (null != criteria.getDeliveryDateFrom()) {
				hsql += " and o.deliveryDate >= :deliveryDateFrom";
				params.put("deliveryDateFrom", criteria.getDeliveryDateFrom());
			}
			if (null != criteria.getDeliveryDateTo()) {
				hsql += " and o.deliveryDate <= :deliveryDateTo";
				params.put("deliveryDateTo", criteria.getDeliveryDateTo());
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

    /* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.saleOrderDao#findBySaleOrderNo(java.lang.String)
	 */
    @Override
    public SaleOrder findBySaleOrderNo(String saleOrderNo) {
		String hsql = "select o from SaleOrder o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		hsql += " and o.documentNumber.documentNo = :saleOrderNo";
		params.put("saleOrderNo", saleOrderNo);
		hsql += " order by o.documentNumber.documentNo";
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		List<SaleOrder> result = queryObj.list();
		if (null != result && result.size() > 0) {
			return result.get(0);
		}
		return null;
	}


}
