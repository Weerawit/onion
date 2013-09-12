package com.worldbestsoft.model;

public class ConstantModel {

	public enum RefType {

		GOOD_RECEIPT("GR"), GOOD_MOVEMENT("GM"), SALE_ORDER("SA"), ADJUST("ADJ"), JOB_ORDER("JB");

		private final String code;
		private String resourceKey;

		private RefType(String code) {
			this.code = code;
		}
		
		private RefType(String code, String resouceKey) {
			this.code = code;
			this.resourceKey = resouceKey;
		}

		public String getCode() {
			return code;
		}

		public String getResourceKey() {
			return resourceKey;
		}
	}

	public enum JobOrderStatus {
		NEW("NEW", "jobOrderStatus.NEW"), INPROGRESS("INPROGRESS", "jobOrderStatus.INPROGRESS"), DONE("DONE", "jobOrderStatus.DONE"), CANCEL("C", "jobOrderStatus.CANCEL");

		private final String code;
		private String key;

		private JobOrderStatus(String code, String key) {
			this.code = code;
			this.key = key;
		}

		public String getCode() {
			return code;
		}

		public String getKey() {
			return key;
		}

	}

	public enum PaymentStatus {
		NONE("1", "paymentStatus.NONE"), PARTAIL_PAID("2", "paymentStatus.PARTAIL_PAID"), FULLY_PAID("3", "paymentStatus.FULLY_PAID");

		private final String code;
		private String key;

		private PaymentStatus(String code, String key) {
			this.code = code;
			this.key = key;
		}

		public String getCode() {
			return code;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}
		
	}

	public enum PaymentType {
		CREDIT("1", "paymentType.CREDIT"), CASH("2", "paymentType.CASH");

		private final String code;
		private String key;

		private PaymentType(String code, String key) {
			this.code = code;
			this.key = key;
		}

		public String getCode() {
			return code;
		}

		public String getKey() {
			return key;
		}
	}

	public enum ReceiptType {

		CASH("1", "receiptType.CASH"), CHEQUE("2", "receiptType.CHEQUE");

		private final String code;
		private String key;

		private ReceiptType(String code, String key) {
			this.code = code;
			this.key = key;
		}

		public String getCode() {
			return code;
		}

		public String getKey() {
			return key;
		}
	}

	public enum SaleOrderStatus {
		ACTIVE("A", "saleOrderStatus.ATCIVE"), CANCEL("C", "saleOrderStatus.CANCEL"), DELIVERY("D", "saleOrderStatus.DELIVERY");

		private final String code;
		private String key;

		private SaleOrderStatus(String code, String key) {
			this.code = code;
			this.key = key;
		}

		public String getCode() {
			return code;
		}

		public String getKey() {
			return key;
		}
	}
	
	public enum SaleReceiptStatus {
		ACTIVE("A", "saleReceiptStatus.ATCIVE"), CANCEL("C", "saleReceiptStatus.CANCEL");

		private final String code;
		private String key;

		private SaleReceiptStatus(String code, String key) {
			this.code = code;
			this.key = key;
		}

		public String getCode() {
			return code;
		}

		public String getKey() {
			return key;
		}
	}


	public enum CustomerType {
		PERSONAL("100", "customerType.PERSONAL"), COMPANY("200", "customerType.COMPANY");

		private final String code;
		private String key;

		private CustomerType(String code, String key) {
			this.code = code;
			this.key = key;
		}

		public String getCode() {
			return code;
		}

		public String getKey() {
			return key;
		}
	}
	
	public enum MovementType {
		FOR_PRODUCTION("100", "movementType.FOR_PRODUCTION");

		private final String code;
		private String key;

		private MovementType(String code, String key) {
			this.code = code;
			this.key = key;
		}

		public String getCode() {
			return code;
		}

		public String getKey() {
			return key;
		}
	}
	
	public enum ItemSockTransactionType {
		COMMIT("COM"), RESERVED("REV"), CANCEL("CAN");

		private final String code;
		private String key;
		
		private ItemSockTransactionType(String code) {
			this.code = code;
		}

		private ItemSockTransactionType(String code, String key) {
			this.code = code;
			this.key = key;
		}

		public String getCode() {
			return code;
		}

		public String getKey() {
			return key;
		}
	}

	public enum GoodsReceiptType {
		PRODUCTION("100", "goodsReceiptType.FROM_PRODUCTION"), PURCHASE("200", "goodsReceiptType.FROM_PURCHASE");

		private final String code;
		private String key;
		
		private GoodsReceiptType(String code) {
			this.code = code;
		}

		private GoodsReceiptType(String code, String key) {
			this.code = code;
			this.key = key;
		}

		public String getCode() {
			return code;
		}

		public String getKey() {
			return key;
		}
	}
}
