package com.worldbestsoft.webapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.worldbestsoft.model.Catalog;
import com.worldbestsoft.model.Customer;
import com.worldbestsoft.model.Employee;
import com.worldbestsoft.model.InvItem;
import com.worldbestsoft.model.SaleOrder;
import com.worldbestsoft.model.Supplier;
import com.worldbestsoft.model.criteria.SaleOrderCriteria;
import com.worldbestsoft.service.CatalogManager;
import com.worldbestsoft.service.CustomerManager;
import com.worldbestsoft.service.EmployeeManager;
import com.worldbestsoft.service.InvItemManager;
import com.worldbestsoft.service.SaleOrderManager;
import com.worldbestsoft.service.SupplierManager;

@Controller
@RequestMapping("/json*")
public class JsonController {
	
	private InvItemManager invItemManager;
	private EmployeeManager employeeManager;
	private CustomerManager customerManager;
	private CatalogManager catalogManager;
	private SaleOrderManager saleOrderManager;
	private SupplierManager supplierManager;
	
	public InvItemManager getInvItemManager() {
		return invItemManager;
	}

	@Autowired
	public void setInvItemManager(InvItemManager invItemManager) {
		this.invItemManager = invItemManager;
	}
	
	public EmployeeManager getEmployeeManager() {
		return employeeManager;
	}

	@Autowired
	public void setEmployeeManager(EmployeeManager employeeManager) {
		this.employeeManager = employeeManager;
	}

	public CustomerManager getCustomerManager() {
		return customerManager;
	}

	@Autowired
	public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}
	
	public CatalogManager getCatalogManager() {
		return catalogManager;
	}

	@Autowired
	public void setCatalogManager(CatalogManager catalogManager) {
		this.catalogManager = catalogManager;
	}
	
	public SaleOrderManager getSaleOrderManager() {
		return saleOrderManager;
	}

	@Autowired
	public void setSaleOrderManager(SaleOrderManager saleOrderManager) {
		this.saleOrderManager = saleOrderManager;
	}
	
	public SupplierManager getSupplierManager() {
		return supplierManager;
	}

	@Autowired
	public void setSupplierManager(SupplierManager supplierManager) {
		this.supplierManager = supplierManager;
	}

	@RequestMapping(value="/item*", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, String>> getInvItemList(@RequestParam("q") String name) {
		InvItem criteria = new InvItem();
		criteria.setName(name);
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		List<InvItem> invItemList = invItemManager.query(criteria, 0, 10, null, null);
		for (InvItem invItem : invItemList) {
			Map<String, String> model = new HashMap<String, String>();
			model.put("name", invItem.getName());
			model.put("code", invItem.getCode());
			resultList.add(model);
		}
		return resultList;
	}
	
	@RequestMapping(value="/employee*", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getEmployeeList(@RequestParam("q") String name) {
		Employee criteria = new Employee();
		criteria.setFirstName(name);
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		List<Employee> employeeList = employeeManager.query(criteria, 0, 10, null, null);
		for (Employee employee : employeeList) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("id", employee.getId().toString());
			model.put("firstName", employee.getFirstName());
			model.put("lastName", employee.getLastName());
			model.put("nickName", employee.getNickName());
			model.put("address", employee.getAddress());
			model.put("fullname", employee.getFullname());
			if (null != employee.getWage()) {
				model.put("wage", employee.getWage());
			} else {
				model.put("wage", "0");
			}
			resultList.add(model);
		}
		return resultList;
	}
	
	@RequestMapping(value="/customer*", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, String>> getCustomerList(@RequestParam("q") String name) {
		Customer criteria = new Customer();
		criteria.setName(name);
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		List<Customer> customerList = customerManager.query(criteria, 0, 10, null, null);
		for (Customer customer : customerList) {
			Map<String, String> model = new HashMap<String, String>();
			model.put("id", customer.getId().toString());
			model.put("name", customer.getName());
			model.put("customerType", customer.getCustomerType());
			model.put("shipingAddress", customer.getShipingAddress());
			model.put("billingAddress", customer.getBillingAddress());
			model.put("contactName", customer.getContactName());
			model.put("contactTel", customer.getContactTel());
			model.put("memo", customer.getMemo());
			resultList.add(model);
		}
		return resultList;
	}

	
	@RequestMapping(value="/catalog*", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, String>> getCatalogList(@RequestParam("q") String name) {
		Catalog criteria = new Catalog();
		criteria.setName(name);
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		List<Catalog> catalogList = catalogManager.query(criteria, 0, 10, null, null);
		for (Catalog catalog : catalogList) {
			Map<String, String> model = new HashMap<String, String>();
			model.put("id", catalog.getId().toString());
			model.put("code", catalog.getCode());
			model.put("name", catalog.getName());
			if (null != catalog.getFinalPrice()) {
				model.put("finalPrice", catalog.getFinalPrice().toString());
			} else {
				model.put("finalPrice", "0");
			}
			resultList.add(model);
		}
		return resultList;
	}
	
	@RequestMapping(value="/saleOrder*", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getSaleOrderList(@RequestParam("q") String name) {
		SaleOrderCriteria criteria = new SaleOrderCriteria();
		criteria.setSaleOrderNo(name);
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		List<SaleOrder> saleOrderList = saleOrderManager.query(criteria, 0, 10, null, null);
		for (SaleOrder saleOrder : saleOrderList) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("id", saleOrder.getId().toString());
			model.put("saleOrderNo", saleOrder.getSaleOrderNo());
			model.put("totalPrice", saleOrder.getTotalPrice());
			model.put("paymentPaid", saleOrder.getPaymentPaid());
			model.put("deliveryStatus", saleOrder.getDeliveryStatus());
			model.put("paymentStatus", saleOrder.getPaymentStatus());
			model.put("deliveryDate", saleOrder.getDeliveryDate());
			
			Map<String, String> customerMap = new HashMap<String, String>();
			customerMap.put("id", saleOrder.getCustomer().getId().toString());
			customerMap.put("name", saleOrder.getCustomer().getName());
			customerMap.put("customerType", saleOrder.getCustomer().getCustomerType());
			customerMap.put("shipingAddress", saleOrder.getCustomer().getShipingAddress());
			customerMap.put("billingAddress", saleOrder.getCustomer().getBillingAddress());
			customerMap.put("contactName", saleOrder.getCustomer().getContactName());
			customerMap.put("contactTel", saleOrder.getCustomer().getContactTel());
			customerMap.put("memo", saleOrder.getCustomer().getMemo());
			model.put("customer", customerMap);
			resultList.add(model);
		}
		return resultList;
	}
	
	@RequestMapping(value="/supplier*", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getSupplierList(@RequestParam("q") String name) {
		Supplier criteria = new Supplier();
		criteria.setName(name);
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		List<Supplier> supplierList = supplierManager.query(criteria, 0, 10, null, null);
		for (Supplier supplier : supplierList) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("id", supplier.getId().toString());
			model.put("code", supplier.getCode());
			model.put("name", supplier.getName());
			model.put("address", supplier.getAddress());
			model.put("contactName", supplier.getContactName());
			model.put("contactTel", supplier.getContactTel());
			resultList.add(model);
		}
		return resultList;
	}


}
