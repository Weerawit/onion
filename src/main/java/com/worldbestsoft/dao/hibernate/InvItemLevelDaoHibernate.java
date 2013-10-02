package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.InvItemLevelDao;
import com.worldbestsoft.model.ConstantModel;
import com.worldbestsoft.model.InvItemLevel;
import com.worldbestsoft.model.criteria.InvItemLevelCriteria;

@Repository("invItemLevelDao")
public class InvItemLevelDaoHibernate extends GenericDaoHibernate<InvItemLevel, Long> implements InvItemLevelDao {

	public InvItemLevelDaoHibernate() {
		super(InvItemLevel.class);
	}
	
    @Override
    public List<InvItemLevel> query(InvItemLevelCriteria criteria, final int page, final int pageSize, final String sortColumn, final String order) {
		String hsql = "select o from InvItemLevel o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (null != criteria) {
			if (null != criteria.getInvItem() && StringUtils.isNotEmpty(criteria.getInvItem().getCode())) {
				hsql += " and o.invItem.code like :invItemCode";
				params.put("invItemCode", criteria.getInvItem().getCode());
			}
			if (null != criteria.getTransactionDateFrom()) {
				hsql += " and o.transactionDate >= :transactionDateFrom";
				params.put("transactionDateFrom", criteria.getTransactionDateFrom());
			}
			if (null != criteria.getTransactionDateTo()) {
				hsql += " and o.transactionDate <= :transactionDateFromTo";
				params.put("transactionDateFromTo", criteria.getTransactionDateTo());
			}
			
			if (null != criteria.getDocumentNumber() && StringUtils.isNotBlank(criteria.getDocumentNumber().getDocumentNo())) {
				hsql += " and o.documentNumber.documentNo = :runningNo ";
				params.put("runningNo", criteria.getDocumentNumber().getDocumentNo());
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
	
    @Override
    public Integer querySize(InvItemLevelCriteria criteria) {
		String hsql = "select count(*) from InvItemLevel o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (null != criteria) {
			if (null != criteria.getInvItem() && StringUtils.isNotEmpty(criteria.getInvItem().getCode())) {
				hsql += " and o.invItem.code like :invItemCode";
				params.put("invItemCode", criteria.getInvItem().getCode());
			}
			if (null != criteria.getTransactionDateFrom()) {
				hsql += " and o.transactionDate >= :transactionDateFrom";
				params.put("transactionDateFrom", criteria.getTransactionDateFrom());
			}
			if (null != criteria.getTransactionDateTo()) {
				hsql += " and o.transactionDate <= :transactionDateFromTo";
				params.put("transactionDateFromTo", criteria.getTransactionDateTo());
			}
			
			if (null != criteria.getDocumentNumber() && StringUtils.isNotBlank(criteria.getDocumentNumber().getDocumentNo())) {
				hsql += " and o.documentNumber.documentNo = :runningNo ";
				params.put("runningNo", criteria.getDocumentNumber().getDocumentNo());
			}
			
		}
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		return ((Number) queryObj.uniqueResult()).intValue();
	}

    @Override
    public List<InvItemLevel> findByRefDocument(String refDocument, ConstantModel.RefType refType, ConstantModel.ItemStockTransactionType transactionType) {
		String hsql = "select o from InvItemLevel o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(refDocument)) {
			hsql += " and o.documentNumber.documentNo = :documentNo";
			params.put("documentNo", refDocument);
		}
		if (null != refType) {
			hsql += " and o.refType = :refType";
			params.put("refType", refType.getCode());
		}
		if (null != transactionType) {
			hsql += " and o.transactionType = :transactionType";
			params.put("transactionType", transactionType.getCode());
		}
		hsql += " order by o.transactionDate";
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		return queryObj.list();
	}

	/* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.InvItemLevelDao#findByInvItemCode(java.lang.String)
	 */
	@Override
    public List<InvItemLevel> findByInvItemCode(String invItemCode) {
		String hsql = "select o from InvItemLevel o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		hsql += " and o.invItem.code = :invItemCode";
		params.put("invItemCode", invItemCode);
		hsql += " order by o.transactionDate";
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		return queryObj.list();
	}

}
