package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.InvGoodsReceiptDao;
import com.worldbestsoft.model.InvGoodsReceipt;
import com.worldbestsoft.model.criteria.InvGoodsReceiptCriteria;

@Repository("invGoodsReceiptDao")
public class InvGoodsReceiptDaoHibernate extends GenericDaoHibernate<InvGoodsReceipt, Long> implements InvGoodsReceiptDao {

	public InvGoodsReceiptDaoHibernate() {
	    super(InvGoodsReceipt.class);
    }
	
    /* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.InvGoodsReceiptDao#query(com.worldbestsoft.model.InvGoodReceipt, int, int, java.lang.String, java.lang.String)
	 */
    @Override
    public List<InvGoodsReceipt> query(InvGoodsReceiptCriteria criteria, final int page, final int pageSize, final String sortColumn, final String order) {
		String hsql = "select o from InvGoodsReceipt o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (null != criteria) {
			if (StringUtils.isNotBlank(criteria.getReceiptType())) {
				hsql += " and o.receiptType = :receiptType";
				params.put("receiptType", criteria.getReceiptType());
			}
			if (null != criteria.getReceiptDateFrom()) {
				hsql += " and o.receiptDate >= :receiptDateFrom";
				params.put("receiptDateFrom", criteria.getReceiptDateFrom());
			}
			if (null != criteria.getReceiptDateTo()) {
				hsql += " and o.receiptDate <= :receiptDateTo";
				params.put("receiptDateTo", criteria.getReceiptDateTo());
			}
			if (null != criteria.getSupplier() && StringUtils.isNotEmpty(criteria.getSupplier().getName())) {
				hsql += " and o.supplier.name like :name";
				params.put("name", criteria.getSupplier().getName() + "%");
			}
			
			if (StringUtils.isNotBlank(criteria.getRunningNo())) {
				hsql += " and o.runningNo = :runningNo ";
				params.put("runningNo", criteria.getRunningNo());
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
	 * @see com.worldbestsoft.dao.hibernate.InvGoodsReceiptDao#querySize(com.worldbestsoft.model.InvGoodReceipt)
	 */
	@Override
    public Integer querySize(InvGoodsReceiptCriteria criteria) {
		String hsql = "select count(*) from InvGoodsReceipt o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (null != criteria) {
			if (StringUtils.isNotBlank(criteria.getReceiptType())) {
				hsql += " and o.receiptType = :receiptType";
				params.put("receiptType", criteria.getReceiptType());
			}
			if (null != criteria.getReceiptDateFrom()) {
				hsql += " and o.receiptDate >= :receiptDateFrom";
				params.put("receiptDateFrom", criteria.getReceiptDateFrom());
			}
			if (null != criteria.getReceiptDateTo()) {
				hsql += " and o.receiptDate <= :receiptDateTo";
				params.put("receiptDateTo", criteria.getReceiptDateTo());
			}
			if (null != criteria.getSupplier() && StringUtils.isNotEmpty(criteria.getSupplier().getName())) {
				hsql += " and o.supplier.name like :name";
				params.put("name", criteria.getSupplier().getName() + "%");
			}
			
			if (StringUtils.isNotBlank(criteria.getRunningNo())) {
				hsql += " and o.runningNo = :runningNo ";
				params.put("runningNo", criteria.getRunningNo());
			}
		}
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		return ((Number) queryObj.uniqueResult()).intValue();
	}

}
