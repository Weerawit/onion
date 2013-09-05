package com.worldbestsoft.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldbestsoft.Constants;
import com.worldbestsoft.dao.LookupDao;
import com.worldbestsoft.model.ConstantModel;
import com.worldbestsoft.model.LabelValue;
import com.worldbestsoft.model.Role;
import com.worldbestsoft.service.LookupManager;

/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("lookupManager")
public class LookupManagerImpl implements LookupManager {
	@Autowired
	LookupDao dao;

	/**
	 * {@inheritDoc}
	 */
	public List<LabelValue> getAllRoles() {
		List<Role> roles = dao.getRoles();
		List<LabelValue> list = new ArrayList<LabelValue>();

		for (Role role1 : roles) {
			list.add(new LabelValue(role1.getName(), role1.getName()));
		}

		return list;
	}

	@Override
	public List<LabelValue> getAllCustomerType(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale);
		List<LabelValue> customerTypeList = new ArrayList<LabelValue>();
		// personal
		customerTypeList.add(new LabelValue(resourceBundle.getString(ConstantModel.CustomerType.PERSONAL.getKey()), ConstantModel.CustomerType.PERSONAL.getCode()));
		// company
		customerTypeList.add(new LabelValue(resourceBundle.getString(ConstantModel.CustomerType.COMPANY.getKey()), ConstantModel.CustomerType.COMPANY.getCode()));
		return customerTypeList;
	}

	@Override
    public List<LabelValue> getAllInvGoodsMovementType(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale);
		List<LabelValue> list = new ArrayList<LabelValue>();
		list.add(new LabelValue(resourceBundle.getString(ConstantModel.MovementType.FOR_PRODUCTION.getKey()), ConstantModel.MovementType.FOR_PRODUCTION.getCode()));
		return list;
	}
	
	@Override
    public List<LabelValue> getAllPaymentType(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale);
		List<LabelValue> paymentTypeList = new ArrayList<LabelValue>();
		// credit
		paymentTypeList.add(new LabelValue(resourceBundle.getString(ConstantModel.PaymentType.CREDIT.getKey()), ConstantModel.PaymentType.CREDIT.getCode()));
		// cash
		paymentTypeList.add(new LabelValue(resourceBundle.getString(ConstantModel.PaymentType.CASH.getKey()), ConstantModel.PaymentType.CASH.getCode()));
		return paymentTypeList;
	}
	
    @Override
    public List<LabelValue> getAllSaleOrderStatusList(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale);
		List<LabelValue> list = new ArrayList<LabelValue>();
		list.add(new LabelValue(resourceBundle.getString(ConstantModel.SaleOrderStatus.ACTIVE.getKey()), ConstantModel.SaleOrderStatus.ACTIVE.getCode()));
		list.add(new LabelValue(resourceBundle.getString(ConstantModel.SaleOrderStatus.CANCEL.getKey()), ConstantModel.SaleOrderStatus.CANCEL.getCode()));
		list.add(new LabelValue(resourceBundle.getString(ConstantModel.SaleOrderStatus.DELIVERY.getKey()), ConstantModel.SaleOrderStatus.DELIVERY.getCode()));
		return list;
	}
    
    @Override
    public List<LabelValue> getAllSaleReceiptStatusList(Locale locale) {
 		ResourceBundle resourceBundle = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale);
 		List<LabelValue> list = new ArrayList<LabelValue>();
 		list.add(new LabelValue(resourceBundle.getString(ConstantModel.SaleReceiptStatus.ACTIVE.getKey()), ConstantModel.SaleReceiptStatus.ACTIVE.getCode()));
 		list.add(new LabelValue(resourceBundle.getString(ConstantModel.SaleReceiptStatus.CANCEL.getKey()), ConstantModel.SaleReceiptStatus.CANCEL.getCode()));
 		return list;
 	}
    

    @Override
    public List<LabelValue> getAllReceiptType(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale);
		List<LabelValue> receiptTypeList = new ArrayList<LabelValue>();
		receiptTypeList.add(new LabelValue(resourceBundle.getString(ConstantModel.ReceiptType.CASH.getKey()), ConstantModel.ReceiptType.CASH.getCode()));
		receiptTypeList.add(new LabelValue(resourceBundle.getString(ConstantModel.ReceiptType.CHEQUE.getKey()), ConstantModel.ReceiptType.CHEQUE.getCode()));
		return receiptTypeList;
	}

    @Override
    public List<LabelValue> getAllJobOrderStatus(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale);
		List<LabelValue> list = new ArrayList<LabelValue>();
		list.add(new LabelValue(resourceBundle.getString(ConstantModel.JobOrderStatus.NEW.getKey()), ConstantModel.JobOrderStatus.NEW.getCode()));
		list.add(new LabelValue(resourceBundle.getString(ConstantModel.JobOrderStatus.INPROGRESS.getKey()), ConstantModel.JobOrderStatus.INPROGRESS.getCode()));
		list.add(new LabelValue(resourceBundle.getString(ConstantModel.JobOrderStatus.DONE.getKey()), ConstantModel.JobOrderStatus.DONE.getCode()));
		list.add(new LabelValue(resourceBundle.getString(ConstantModel.JobOrderStatus.DELIVERY.getKey()), ConstantModel.JobOrderStatus.DELIVERY.getCode()));
		return list;
	}
    
    @Override
    public List<LabelValue> getAllPaymentStatus(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale);
		List<LabelValue> list = new ArrayList<LabelValue>();
		list.add(new LabelValue(resourceBundle.getString(ConstantModel.PaymentStatus.NONE.getKey()), ConstantModel.PaymentStatus.NONE.getCode()));
		list.add(new LabelValue(resourceBundle.getString(ConstantModel.PaymentStatus.PARTAIL_PAID.getKey()), ConstantModel.PaymentStatus.PARTAIL_PAID.getCode()));
		list.add(new LabelValue(resourceBundle.getString(ConstantModel.PaymentStatus.FULLY_PAID.getKey()), ConstantModel.PaymentStatus.FULLY_PAID.getCode()));
		return list;
	}
    
    @Override
    public List<LabelValue> getAllGoodsReceiptType(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale);
		List<LabelValue> list = new ArrayList<LabelValue>();
		list.add(new LabelValue(resourceBundle.getString(ConstantModel.GoodsReceiptType.PRODUCTION.getKey()), ConstantModel.GoodsReceiptType.PRODUCTION.getCode()));
		list.add(new LabelValue(resourceBundle.getString(ConstantModel.GoodsReceiptType.PURCHASE.getKey()), ConstantModel.GoodsReceiptType.PURCHASE.getCode()));
		return list;
	}

}
