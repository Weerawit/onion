package com.worldbestsoft.webapp.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.worldbestsoft.model.Customer;
import com.worldbestsoft.service.CustomerManager;
import com.worldbestsoft.service.LookupManager;

@Controller
@RequestMapping("/customer*")
public class CustomerFormController extends BaseFormController {
	private CustomerManager customerManager;
	private LookupManager lookupManager;

	public CustomerManager getCustomerManager() {
		return customerManager;
	}

	@Autowired
	public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}
	
	public LookupManager getLookupManager() {
		return lookupManager;
	}

	@Autowired
	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(Customer customerForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("cancel") != null) {
			return new ModelAndView("redirect:/customerList");
		}

		if (validator != null) { // validator is null during testing
			validator.validate(customerForm, errors);

			if (errors.hasErrors() && !StringUtils.equalsIgnoreCase("delete", request.getParameter("action"))) { // don't validate when deleting
				return new ModelAndView("customer", "customer", customerForm);
			}
		}
		log.info(request.getRemoteUser() + " is saving Customer := " + customerForm);

		Locale locale = request.getLocale();

		if (StringUtils.equalsIgnoreCase("delete", request.getParameter("action"))) {
			//since code input is readonly, no value pass to form then we need to query from db.
//			Customer customer = getCustomerManager().get(customerForm.getId());
			getCustomerManager().remove(customerForm.getId());
			saveMessage(request, getText("customer.deleted", customerForm.getName(), locale));
			return new ModelAndView("redirect:/customerList");
		} else {
			
			if (null == customerForm.getId()) {
				// add
				customerForm.setCreateDate(new Date());
				customerForm.setCreateUser(request.getRemoteUser());

				customerForm = getCustomerManager().save(customerForm);

				saveMessage(request, getText("customer.added", customerForm.getName(), locale));
				return new ModelAndView("redirect:/customer").addObject("id", customerForm.getId());
			} else {
				// edit

				Customer customer = getCustomerManager().get(customerForm.getId());
				customer.setName(customerForm.getName());
				customer.setCustomerType(customerForm.getCustomerType());
				customer.setShipingAddress(customerForm.getShipingAddress());
				customer.setBillingAddress(customerForm.getBillingAddress());
				customer.setContactName(customerForm.getContactName());
				customer.setContactTel(customerForm.getContactTel());
				customer.setMemo(customerForm.getMemo());
				
				customer.setUpdateDate(new Date());
				customer.setUpdateUser(request.getRemoteUser());
				customer = getCustomerManager().save(customer);

				request.setAttribute("customer", customer);
				saveMessage(request, getText("customer.saved", customer.getName(), locale));
				return new ModelAndView("redirect:/customerList");
			}
		}
	}

	@RequestMapping(method = { RequestMethod.GET })
	protected ModelAndView display(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Customer customer = new Customer();
		if (!isFormSubmission(request)) {
			if (id != null) {
				customer = getCustomerManager().get(Long.valueOf(id));
			}
		} else {
			customer = getCustomerManager().get(Long.valueOf(id));
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("customer", customer);
		model.put("customerTypeList", lookupManager.getAllCustomerType(request.getLocale()));
		return new ModelAndView("customer", model);
	}

	private boolean isFormSubmission(HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("post");
	}

}
