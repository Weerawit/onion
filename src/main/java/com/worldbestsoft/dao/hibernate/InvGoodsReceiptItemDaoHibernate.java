package com.worldbestsoft.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.InvGoodsReceiptItemDao;
import com.worldbestsoft.model.InvGoodReceiptItem;

@Repository("invGoodsReceiptItemDao")
public class InvGoodsReceiptItemDaoHibernate extends GenericDaoHibernate<InvGoodReceiptItem, Long> implements InvGoodsReceiptItemDao {

	public InvGoodsReceiptItemDaoHibernate() {
	    super(InvGoodReceiptItem.class);
    }
	
	@Override
    public List<InvGoodReceiptItem> findByInvGoodReceipt(Long id) {
		String hsql = "select o from InvGoodReceiptItem o where 1=1 ";
		final Map<String, Object> params = new HashMap<String, Object>();
		hsql += " and o.invGoodReceipt.id = :id";
		params.put("id", id);
		hsql += " order by o.id";
		Query queryObj = getSession().createQuery(hsql);
		queryObj.setProperties(params);
		return queryObj.list();
	} 
	
}
