package com.worldbestsoft.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.dao.InvItemDao;
import com.worldbestsoft.dao.InvItemLevelDao;
import com.worldbestsoft.dao.InvStockDao;
import com.worldbestsoft.model.ConstantModel;
import com.worldbestsoft.model.ConstantModel.ItemSockTransactionType;
import com.worldbestsoft.model.InvItemLevel;
import com.worldbestsoft.model.InvStock;
import com.worldbestsoft.service.InvStockManager;

@Service("invStockManager")
public class InvStockManagerImpl implements InvStockManager {
	
	private InvStockDao invStockDao;
	private InvItemLevelDao invItemLevelDao;
	private InvItemDao invItemDao;

	public InvStockDao getInvStockDao() {
		return invStockDao;
	}

	@Autowired
	public void setInvStockDao(InvStockDao invStockDao) {
		this.invStockDao = invStockDao;
	}
	
	public InvItemLevelDao getInvItemLevelDao() {
		return invItemLevelDao;
	}

	@Autowired
	public void setInvItemLevelDao(InvItemLevelDao invItemLevelDao) {
		this.invItemLevelDao = invItemLevelDao;
	}
	
	public InvItemDao getInvItemDao() {
		return invItemDao;
	}

	@Autowired
	public void setInvItemDao(InvItemDao invItemDao) {
		this.invItemDao = invItemDao;
	}
	
	@Override
    public void cancelReserved(String documentNumber, ConstantModel.RefType documentType, String user) {
		List<InvItemLevel> reservedList = invItemLevelDao.findByRefDocument(documentNumber, documentType, ItemSockTransactionType.RESERVED);
		if (null != reservedList) {
			for (InvItemLevel reservedInvItemLevel : reservedList) {
				InvItemLevel cancelInvItemLevel = new InvItemLevel();
				cancelInvItemLevel.setTransactionDate(new Date());
				//reverse qty
				cancelInvItemLevel.setQtyAvailableAdjust(reservedInvItemLevel.getQtyAvailableAdjust().multiply(BigDecimal.valueOf(-1)));
				cancelInvItemLevel.setInvItem(reservedInvItemLevel.getInvItem());
				cancelInvItemLevel.setRefDocument(reservedInvItemLevel.getRefDocument());
				cancelInvItemLevel.setRefType(reservedInvItemLevel.getRefType());
				cancelInvItemLevel.setTransactionType(ConstantModel.ItemSockTransactionType.CANCEL.getCode());
				cancelInvItemLevel.setUpdateUser(user);
				updateStock(cancelInvItemLevel);
			}
		}
	}


	/* (non-Javadoc)
	 * @see com.worldbestsoft.service.impl.InvStockManager#updateStock(com.worldbestsoft.model.InvItemLevel)
	 */
	@Override
	public InvStock updateStock(InvItemLevel invItemLevel) {
		//TODO:handle synchronized
		InvStock invStock = invStockDao.findByInvItemCode(invItemLevel.getInvItem().getCode());
		if (null == invStock) {
			invStock = new InvStock();
			invStock.setInvItem(invItemLevel.getInvItem());
			invStock.setQty(BigDecimal.ZERO);
			invStock.setQtyAvailable(BigDecimal.ZERO);
		}
		invItemLevel.setQtyBefore(invStock.getQty());
		BigDecimal qtyAfter = invStock.getQty();
		if (null != invItemLevel.getQtyAdjust()) {
			qtyAfter = invStock.getQty().add(invItemLevel.getQtyAdjust());
		}
		invItemLevel.setQtyAfter(qtyAfter);
		invStock.setQty(qtyAfter);
		
		invItemLevel.setQtyAvailableBefore(invStock.getQtyAvailable());
		BigDecimal qtyAvailableAfter = invStock.getQtyAvailable();
		if (null != invItemLevel.getQtyAvailableAdjust()) {
			qtyAvailableAfter = invStock.getQtyAvailable().add(invItemLevel.getQtyAvailableAdjust());
		}
		invItemLevel.setQtyAvailableAfter(qtyAvailableAfter);
		invStock.setQtyAvailable(qtyAvailableAfter);
		
		invItemLevel = invItemLevelDao.save(invItemLevel);
		
		invStock.setUpdateDate(new Date());
		invStock.setUpdateUser(invItemLevel.getUpdateUser());
		
		return invStockDao.save(invStock);
	}
	
	@Override
    public Integer querySize(InvStock criteria) {
	    return invStockDao.querySize(criteria);
    }

	@Override
    public List<InvStock> query(InvStock criteria, int page, int pageSize, String sortColumn, String order) {
	    return invStockDao.query(criteria, page, pageSize, sortColumn, order);
    }

    public void save(InvStock invStock) {
		InvItemLevel invItemLevel = new InvItemLevel();
		invItemLevel.setInvItem(invStock.getInvItem());
		invItemLevel.setQtyAdjust(invStock.getQty());
		invItemLevel.setQtyAvailableAdjust(invStock.getQty());
		invItemLevel.setTransactionDate(new Date());
		invItemLevel.setUpdateUser(invStock.getUpdateUser());
		invItemLevel.setRefType(ConstantModel.RefType.ADJUST.getCode());
		invItemLevel.setTransactionType(ConstantModel.ItemSockTransactionType.COMMIT.getCode());

		updateStock(invItemLevel);
    }

	/**
	 * For get current Qty
	 * @param invItemCode
	 * @return
	 */
	@Override
    public InvStock findByInvItemCode(String invItemCode) {
		//used to get current qty for item.
		//refactor if needed.
	    return invStockDao.findByInvItemCode(invItemCode);
    }

	@Override
    public InvStock get(Long id) {
	    return invStockDao.get(id);
    }

	
}
