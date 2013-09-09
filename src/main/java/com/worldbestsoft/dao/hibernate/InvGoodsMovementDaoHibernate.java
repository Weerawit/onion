package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.InvGoodsMovementDao;
import com.worldbestsoft.model.InvGoodsMovement;
import com.worldbestsoft.model.criteria.InvGoodsMovementCriteria;

@Repository("invGoodsMovementDao")
public class InvGoodsMovementDaoHibernate extends GenericDaoHibernate<InvGoodsMovement, Long> implements InvGoodsMovementDao {

	public InvGoodsMovementDaoHibernate() {
	    super(InvGoodsMovement.class);
    }
	
    /* (non-Javadoc)
	 * @see com.worldbestsoft.dao.hibernate.InvGoodsMovementDao#query(com.worldbestsoft.model.criteria.InvGoodsMovementCriteria, int, int, java.lang.String, java.lang.String)
	 */
    @Override
    public List<InvGoodsMovement> query(InvGoodsMovementCriteria criteria, final int page, final int pageSize, final String sortColumn, final String order) {
		String hsql = "select o from InvGoodsMovement o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (null != criteria) {
			
			if (null != criteria.getMovementDateFrom()) {
				hsql += " and o.movementDate >= :movementDateFrom";
				params.put("movementDateFrom", criteria.getMovementDateFrom());
			}
			if (null != criteria.getMovementDateTo()) {
				hsql += " and o.movementDate <= :movementDateTo";
				params.put("movementDateTo", criteria.getMovementDateTo());
			}
			if (StringUtils.isNotEmpty(criteria.getOwner())) {
				hsql += " and o.owner like :owner";
				params.put("owner", criteria.getOwner() + "%");
			}
			
			if (null != criteria.getDocumentNumber() && StringUtils.isNotBlank(criteria.getDocumentNumber().getDocumentNo())) {
				hsql += " and o.documentNumber.documentNo = :runningNo ";
				params.put("runningNo", criteria.getDocumentNumber().getDocumentNo());
			}
			
			if (StringUtils.isNotBlank(criteria.getMovementType())) {
				hsql += " and o.movementType = :movementType ";
				params.put("movementType", criteria.getMovementType());
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
	 * @see com.worldbestsoft.dao.hibernate.InvGoodsMovementDao#querySize(com.worldbestsoft.model.criteria.InvGoodsMovementCriteria)
	 */
    @Override
    public Integer querySize(InvGoodsMovementCriteria criteria) {
		String hsql = "select count(*) from InvGoodsMovement o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (null != criteria) {
			
			if (null != criteria.getMovementDateFrom()) {
				hsql += " and o.movementDate >= :movementDateFrom";
				params.put("movementDateFrom", criteria.getMovementDateFrom());
			}
			if (null != criteria.getMovementDateTo()) {
				hsql += " and o.movementDate <= :movementDateTo";
				params.put("movementDateTo", criteria.getMovementDateTo());
			}
			if (StringUtils.isNotEmpty(criteria.getOwner())) {
				hsql += " and o.owner like :owner";
				params.put("owner", criteria.getOwner() + "%");
			}
			
			if (null != criteria.getDocumentNumber() && StringUtils.isNotBlank(criteria.getDocumentNumber().getDocumentNo())) {
				hsql += " and o.documentNumber.documentNo = :runningNo ";
				params.put("runningNo", criteria.getDocumentNumber().getDocumentNo());
			}
			
			if (StringUtils.isNotBlank(criteria.getMovementType())) {
				hsql += " and o.movementType = :movementType ";
				params.put("movementType", criteria.getMovementType());
			}
		}
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		return ((Number) queryObj.uniqueResult()).intValue();
	}

}
