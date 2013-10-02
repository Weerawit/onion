package com.worldbestsoft.service;

import java.util.List;
import java.util.Locale;

import com.worldbestsoft.model.LabelValue;

/**
 * Business Service Interface to talk to persistence layer and
 * retrieve values for drop-down choice lists.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface LookupManager {
    /**
     * Retrieves all possible roles from persistence layer
     * @return List of LabelValue objects
     */
    List<LabelValue> getAllRoles();

	public abstract List<LabelValue> getAllCustomerType(Locale locale);

	public abstract List<LabelValue> getAllInvGoodsMovementType(Locale locale);

	public abstract List<LabelValue> getAllPaymentType(Locale locale);

	public abstract List<LabelValue> getAllSaleOrderStatusList(Locale locale);

	public abstract List<LabelValue> getAllReceiptType(Locale locale);

	public abstract List<LabelValue> getAllJobOrderStatus(Locale locale);

	public abstract List<LabelValue> getAllPaymentStatus(Locale locale);

	public abstract List<LabelValue> getAllGoodsReceiptType(Locale locale);

	public abstract List<LabelValue> getAllSaleReceiptStatusList(Locale locale);

	public abstract List<LabelValue> getAllItemStockTransactionType(Locale locale);

	public abstract List<LabelValue> getAllRefType(Locale locale);
}
