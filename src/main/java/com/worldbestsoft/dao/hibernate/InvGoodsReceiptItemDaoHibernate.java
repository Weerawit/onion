package com.worldbestsoft.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.worldbestsoft.dao.InvGoodsReceiptItemDao;
import com.worldbestsoft.model.InvGoodReceiptItem;

@Repository("invGoodsReceiptItemDao")
public class InvGoodsReceiptItemDaoHibernate extends GenericDaoHibernate<InvGoodReceiptItem, Long> implements InvGoodsReceiptItemDao {

	public InvGoodsReceiptItemDaoHibernate() {
	    super(InvGoodReceiptItem.class);
    }
	
}
