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

@Repository("invItemLevelDao")
public class InvItemLevelDaoHibernate extends GenericDaoHibernate<InvItemLevel, Long> implements InvItemLevelDao {

	public InvItemLevelDaoHibernate() {
		super(InvItemLevel.class);
	}

    @Override
    public List<InvItemLevel> findByRefDocument(String refDocument, ConstantModel.RefType refType, ConstantModel.ItemSockTransactionType transactionType) {
		String hsql = "select o from InvItemLevel o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(refDocument)) {
			hsql += " and o.refDocument = :refDocument";
			params.put("refDocument", refDocument);
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
