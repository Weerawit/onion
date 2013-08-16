package com.worldbestsoft.webapp.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.worldbestsoft.model.Customer;
import com.worldbestsoft.model.InvItem;
import com.worldbestsoft.model.SaleOrder;
import com.worldbestsoft.service.CustomerManager;
import com.worldbestsoft.service.LookupManager;
import com.worldbestsoft.service.SaleOrderManager;

@Controller
@RequestMapping("/saleOrder*")
public class SaleOrderFormController extends BaseFormController {

	private SaleOrderManager saleOrderManager;
	private LookupManager lookupManager;
	private CustomerManager customerManager;

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

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("saleOrder") SaleOrder saleOrderForm, BindingResult errors, HttpServletRequest request) throws Exception {
		if (request.getParameter("cancel") != null) {
			return new ModelAndView("redirect:/saleOrderList");
		}
		
		HttpSession session = request.getSession();
		SaleOrder saleOrderSession = (SaleOrder) session.getAttribute("saleOrder");
		

		if (validator != null) { // validator is null during testing
			validator.validate(saleOrderForm, errors);
			
			if (null != saleOrderForm.getCustomer()) {
				Customer customer = getCustomerManager().get(saleOrderForm.getCustomer().getId());
				saleOrderForm.setCustomer(customer);
				if (null == customer) {
					errors.rejectValue("customer.name", "errors.invalid",new Object[] { getText("saleOrder.customer.name", request.getLocale())}, "errors.invalid");	
				}
			}

			if (errors.hasErrors() && request.getParameter("delete") == null) { // don't
																				// validate
																				// when
																				// deleting
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("saleOrder", saleOrderForm);
				model.put("paymentTypeList", lookupManager.getAllPaymentType(request.getLocale()));
				model.put("saleOrderItemList", saleOrderSession.getSaleOrderItems());
				model.put("saleOrderStatusList", lookupManager.getAllSaleOrderStatusList(request.getLocale()));
				return new ModelAndView("saleOrder", model);
			}
		}
		log.info(request.getRemoteUser() + " is saving SaleOrder := " + saleOrderForm);

		Locale locale = request.getLocale();

		if (request.getParameter("delete") != null) {
			// since code input is readonly, no value pass to form then we need
			// to query from db.
			SaleOrder saleOrder = getSaleOrderManager().get(saleOrderForm.getId());
			getSaleOrderManager().remove(saleOrderForm.getId());
			saveMessage(request, getText("saleOrder.deleted", saleOrder.getSaleOrderNo(), locale));
			return new ModelAndView("redirect:/saleOrderList");
		} else {

			if (null == saleOrderForm.getId()) {
				SaleOrder saleOrder = new SaleOrder();
				PropertyUtils.copyProperties(saleOrder, saleOrderForm);
				// add
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
				saleOrder.setStatus(saleOrderForm.getStatus());
				saleOrder.setTotalPrice(saleOrderForm.getTotalPrice());
				saleOrder.setCustomer(saleOrderForm.getCustomer());
				
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
		}
		
		if (!isFormSubmission(request)) {
			if (id != null) {
				saleOrder = getSaleOrderManager().get(Long.valueOf(id));
			}
		} else {
			saleOrder = getSaleOrderManager().get(Long.valueOf(id));
		}
		session.setAttribute("saleOrder", saleOrder);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("saleOrder", saleOrder);
		model.put("paymentTypeList", lookupManager.getAllPaymentType(request.getLocale()));
		model.put("saleOrderItemList", saleOrder.getSaleOrderItems());
		model.put("saleOrderStatusList", lookupManager.getAllSaleOrderStatusList(request.getLocale()));
		return new ModelAndView("saleOrder", model);
	}

	private boolean isFormSubmission(HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("post");
	}
	
	


}
