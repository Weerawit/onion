package com.worldbestsoft.webapp.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.worldbestsoft.model.Catalog;
import com.worldbestsoft.model.Customer;
import com.worldbestsoft.model.SaleOrder;
import com.worldbestsoft.model.SaleOrderItem;
import com.worldbestsoft.service.CatalogManager;
import com.worldbestsoft.service.CustomerManager;
import com.worldbestsoft.service.LookupManager;
import com.worldbestsoft.service.SaleOrderManager;

@Controller
@RequestMapping("/saleOrder*")
public class SaleOrderFormController extends BaseFormController {

	private SaleOrderManager saleOrderManager;
	private LookupManager lookupManager;
	private CustomerManager customerManager;
	private CatalogManager catalogManager;

	public SaleOrderManager getSaleOrderManager() {
		return saleOrderManager;
	}

	@Autowired
	public void setSaleOrderManager(SaleOrderManager saleOrderManager) {
		this.saleOrderManager = saleOrderManager;
	}
	
	public LookupManager getLookupManager() {
		return lookupManager;
	}

	@Autowired
	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
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

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("saleOrder") SaleOrder saleOrderForm, BindingResult errors, HttpServletRequest request) throws Exception {
		if (request.getParameter("cancel") != null) {
			return new ModelAndView("redirect:/saleOrderList");
		}
		
		HttpSession session = request.getSession();
		SaleOrder saleOrderSession = (SaleOrder) session.getAttribute("saleOrder");
		

		if (validator != null) { // validator is null during testing
			validator.validate(saleOrderForm, errors);
			
			//validate only for add
			if (saleOrderForm.getId() == null) {
			
				if (null != saleOrderForm.getCustomer()) {
					Customer customer = getCustomerManager().get(saleOrderForm.getCustomer().getId());
					saleOrderForm.setCustomer(customer);
					if (null == customer) {
						errors.rejectValue("customer.name", "errors.invalid", new Object[] { getText("saleOrder.customer.name", request.getLocale())}, "errors.invalid");	
					}
				}
				
				BigDecimal totalPrice = BigDecimal.ZERO;
				boolean foundSaleOrderItem = false;
				if (null != saleOrderSession.getSaleOrderItems()) {
					for (SaleOrderItem saleOrderItem : saleOrderSession.getSaleOrderItems()) {
						if (null != saleOrderItem.getCatalog() && StringUtils.isNotBlank(saleOrderItem.getCatalog().getCode())) {
							Catalog catalog = catalogManager.findByCatalogCode(saleOrderItem.getCatalog().getCode());
							if (null == catalog) {
								errors.rejectValue("saleOrderItems", "errors.invalid",new Object[] { getText("saleOrderItem.catalog.code", request.getLocale())}, "errors.invalid");
							} else {
								saleOrderItem.setPrice(saleOrderItem.getPricePerUnit().multiply(saleOrderItem.getQty()));
								totalPrice = totalPrice.add(saleOrderItem.getPrice());
								foundSaleOrderItem = true;
							}
						}
					}
				}
				
				saleOrderForm.setTotalPrice(totalPrice);
				
				if (!foundSaleOrderItem) {
					errors.rejectValue("saleOrderItems", "errors.required",new Object[] { getText("saleOrder.saleOrerItem", request.getLocale())}, "errors.required");
				}
			}

			if (errors.hasErrors() && !StringUtils.equalsIgnoreCase("delete", request.getParameter("action"))) { // don't validate when deleting
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("saleOrder", saleOrderForm);
				model.put("paymentTypeList", lookupManager.getAllPaymentType(request.getLocale()));
				model.put("saleOrderItemList", saleOrderSession.getSaleOrderItems());
				return new ModelAndView("saleOrder", model);
			}
		}
		log.info(request.getRemoteUser() + " is saving SaleOrder := " + saleOrderForm);

		Locale locale = request.getLocale();

		if (StringUtils.equalsIgnoreCase("delete", request.getParameter("action"))) {
			// since code input is readonly, no value pass to form then we need
			// to query from db.
			getSaleOrderManager().remove(saleOrderForm.getId(), request.getRemoteUser(), saleOrderForm.getCancelReason());
			saveMessage(request, getText("saleOrder.deleted", saleOrderForm.getSaleOrderNo(), locale));
			return new ModelAndView("redirect:/saleOrderList");
		} else {

			if (null == saleOrderForm.getId()) {
				SaleOrder saleOrder = new SaleOrder();
				PropertyUtils.copyProperties(saleOrder, saleOrderForm);
				// add
				saleOrder.setPaymentPaid(BigDecimal.ZERO);
				saleOrder.setCreateDate(new Date());
				saleOrder.setCreateUser(request.getRemoteUser());
				
				
				saleOrder = getSaleOrderManager().save(saleOrder, saleOrderSession.getSaleOrderItems());

				saveMessage(request, getText("saleOrder.added", saleOrder.getSaleOrderNo(), locale));
				return new ModelAndView("redirect:/saleOrder").addObject("id", saleOrder.getId());
			} else {
				// edit

				SaleOrder saleOrder = getSaleOrderManager().get(saleOrderForm.getId());
				saleOrder.setDeliveryDate(saleOrderForm.getDeliveryDate());
				saleOrder.setPaymentType(saleOrderForm.getPaymentType());
				//saleOrder.setTotalPrice(saleOrderForm.getTotalPrice());
				//saleOrder.setCustomer(saleOrderForm.getCustomer());
				
				saleOrder.setUpdateDate(new Date());
				saleOrder.setUpdateUser(request.getRemoteUser());
				
				saleOrder = getSaleOrderManager().save(saleOrder, saleOrderSession.getSaleOrderItems());

				request.setAttribute("saleOrder", saleOrder);
				saveMessage(request, getText("saleOrder.saved", saleOrder.getSaleOrderNo(), locale));
				return new ModelAndView("redirect:/saleOrderList");
			}
		}
	}

	@RequestMapping(method = { RequestMethod.GET })
	protected ModelAndView display(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String method = request.getParameter("method");
		HttpSession session = request.getSession();
		
		SaleOrder saleOrder = (SaleOrder) session.getAttribute("saleOrder");
		if (null == saleOrder || StringUtils.equalsIgnoreCase(method, "add")) {
			saleOrder = new SaleOrder();
			SaleOrderItem item = new SaleOrderItem();
			Catalog catalog = new Catalog();
			catalog.setId(Long.valueOf(0));
			item.setCatalog(catalog);
			item.setQty(BigDecimal.ZERO);
			item.setPricePerUnit(BigDecimal.ZERO);
			saleOrder.getSaleOrderItems().add(item);
		}
		
		if (!isFormSubmission(request)) {
			if (id != null) {
				saleOrder = getSaleOrderManager().get(Long.valueOf(id));
			}
		} else {
			saleOrder = getSaleOrderManager().get(Long.valueOf(id));
		}
		
		BigDecimal totalPrice = BigDecimal.ZERO;
		for (SaleOrderItem saleOrderItem : saleOrder.getSaleOrderItems()) {
			if (null != saleOrderItem.getPrice()) {
				totalPrice = totalPrice.add(saleOrderItem.getPrice());
			}
		}
		saleOrder.setTotalPrice(totalPrice);
		session.setAttribute("saleOrder", saleOrder);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("saleOrder", saleOrder);
		model.put("paymentTypeList", lookupManager.getAllPaymentType(request.getLocale()));
		model.put("saleOrderItemList", saleOrder.getSaleOrderItems());
		return new ModelAndView("saleOrder", model);
	}

	private boolean isFormSubmission(HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("post");
	}
	
	
	
	
	@RequestMapping(value = "/displayTable")
	protected ModelAndView displayTable(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		SaleOrder saleOrder = (SaleOrder) session.getAttribute("saleOrder");
		Map<String, Object> model = new HashMap<String, Object>();
		
		BigDecimal totalPrice = BigDecimal.ZERO;
		for (SaleOrderItem saleOrderItem : saleOrder.getSaleOrderItems()) {
			if (null != saleOrderItem.getPrice()) {
				totalPrice = totalPrice.add(saleOrderItem.getPrice());
			}
		}
		saleOrder.setTotalPrice(totalPrice);
		model.put("saleOrderItemList", saleOrder.getSaleOrderItems());
		model.put("saleOrder", saleOrder);
		return new ModelAndView("saleOrderTable", model);
	}
	
	@RequestMapping(value = "/addRow")
	protected ModelAndView addRow(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		SaleOrder saleOrder = (SaleOrder) session.getAttribute("saleOrder");
		SaleOrderItem item = new SaleOrderItem();
		Catalog catalog = new Catalog();
		catalog.setId(Long.valueOf(0));
		item.setCatalog(catalog);
		item.setQty(BigDecimal.ZERO);
		item.setPricePerUnit(BigDecimal.ZERO);
		saleOrder.getSaleOrderItems().add(item);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("saleOrderItemList", saleOrder.getSaleOrderItems());
		model.put("saleOrder", saleOrder);
		return displayTable(request, response);
	}
	
	@RequestMapping(value = "/deleteRow")
	public ModelAndView deleteRow(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] checkbox = request.getParameterValues("checkbox");
		HttpSession session = request.getSession();
		SaleOrder saleOrder = (SaleOrder) session.getAttribute("saleOrder");
		List<SaleOrderItem> saleOrderItemList = new ArrayList<SaleOrderItem>(saleOrder.getSaleOrderItems());
		
		if (null != checkbox && checkbox.length > 0) {
			for (int i = checkbox.length - 1; i >= 0; i--) {
				saleOrderItemList.remove(Integer.parseInt(checkbox[i]));
			}
		}
		saleOrder.setSaleOrderItems(new LinkedHashSet<SaleOrderItem>(saleOrderItemList));
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("saleOrderItemList", saleOrder.getSaleOrderItems());
		model.put("saleOrder", saleOrder);
		return displayTable(request, response);
	}
	
	@RequestMapping(value = "/updateRow")
	public ModelAndView updateRow(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		SaleOrder saleOrder = (SaleOrder) session.getAttribute("saleOrder");
		List<SaleOrderItem> saleOrderItemList = new ArrayList<SaleOrderItem>(saleOrder.getSaleOrderItems());
		
		String index = request.getParameter("index");// specific element
		
		if (StringUtils.isNotBlank(index)) {
			String code = request.getParameter("catalog.code");
			String qtysString = request.getParameter("qty");
			String pricePerUnitsString = request.getParameter("pricePerUnit");
			
			if (StringUtils.isNotBlank(code)) {
				SaleOrderItem foundSaleOrderItem = (SaleOrderItem) CollectionUtils.find(saleOrderItemList, new BeanPropertyValueEqualsPredicate("catalog.code", code, true));
				if (null == foundSaleOrderItem) {
					//if not in the list get from index, otherwise update old one.
					foundSaleOrderItem = saleOrderItemList.get(Integer.parseInt(index));
				}
				Catalog catalog = catalogManager.findByCatalogCode(code);
				foundSaleOrderItem.setCatalog(catalog);
				BigDecimal qty = null;
				BigDecimal pricePerUnit = null;
				if (StringUtils.isNotBlank(qtysString)) {
					try {
						qty = NumberUtils.createBigDecimal(qtysString);
					} catch (NumberFormatException e) {
						qty = BigDecimal.ZERO;
					}
					foundSaleOrderItem.setQty(qty);
				} 
				if (StringUtils.isNotBlank(pricePerUnitsString)) {
					try {
						pricePerUnit = NumberUtils.createBigDecimal(pricePerUnitsString);
					} catch (NumberFormatException e) {
						pricePerUnit = BigDecimal.ZERO;
					}
					foundSaleOrderItem.setPricePerUnit(pricePerUnit);
				}
				if (null != qty && null != pricePerUnit) {
					foundSaleOrderItem.setPrice(qty.multiply(pricePerUnit));
				}
			} else {
				SaleOrderItem foundSaleOrderItem = saleOrderItemList.get(Integer.parseInt(index));
				foundSaleOrderItem.setCatalog(null);
				foundSaleOrderItem.setQty(null);
				foundSaleOrderItem.setPrice(null);
				foundSaleOrderItem.setPricePerUnit(null);
			}
		} else {
			String[] codes = request.getParameterValues("catalog.code");
			String[] qtys = request.getParameterValues("qty");
			String[] pricePerUnits = request.getParameterValues("pricePerUnit");
			
			for (int i = 0; i < codes.length; i++) {
				if (StringUtils.isNotBlank(codes[i])) {
					SaleOrderItem foundSaleOrderItem = (SaleOrderItem) CollectionUtils.find(saleOrderItemList, new BeanPropertyValueEqualsPredicate("catalog.code", codes[i], true));
					if (null == foundSaleOrderItem) {
						foundSaleOrderItem = new SaleOrderItem();
						Catalog catalog = catalogManager.findByCatalogCode(codes[i]);
						foundSaleOrderItem.setCatalog(catalog);
						saleOrderItemList.add(foundSaleOrderItem);
					} 
					BigDecimal qty = null;
					BigDecimal pricePerUnit = null;
					if (StringUtils.isNotBlank(qtys[i])) {
						try {
							qty = NumberUtils.createBigDecimal(qtys[i]);
						} catch (NumberFormatException e) {
							qty = BigDecimal.ZERO;
						}
						foundSaleOrderItem.setQty(qty);
					} 
					if (StringUtils.isNotBlank(pricePerUnits[i])) {
						try {
							pricePerUnit = NumberUtils.createBigDecimal(pricePerUnits[i]);
						} catch (NumberFormatException e) {
							pricePerUnit = BigDecimal.ZERO;
						}
						foundSaleOrderItem.setPricePerUnit(pricePerUnit);
					}
				
					if (null != qty && null != pricePerUnit) {
						foundSaleOrderItem.setPrice(qty.multiply(pricePerUnit));
					}
				}
			}
		}
		
		saleOrder.setSaleOrderItems(new LinkedHashSet<SaleOrderItem>(saleOrderItemList));
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("saleOrderItemList", saleOrder.getSaleOrderItems());
		model.put("saleOrder", saleOrder);
		return displayTable(request, response);
	}

}
