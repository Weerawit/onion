package com.worldbestsoft.webapp.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.worldbestsoft.model.Customer;
import com.worldbestsoft.service.CustomerManager;
import com.worldbestsoft.service.LookupManager;

@Controller
@RequestMapping("/customerList*")
public class CustomerListController extends BaseFormController {

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

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Customer criteria = new Customer();
		criteria.setName(request.getParameter("name"));
		criteria.setContactName(request.getParameter("contactName"));
		criteria.setContactTel(request.getParameter("contactTel"));
		criteria.setCustomerType(request.getParameter("customerType"));
		int page = getPage(request);
		String sortColumn = getSortColumn(request);
		String order = getSortOrder(request);
		int size = getPageSize(request);

		log.info(request.getRemoteUser() + " is quering Customer criteria := " + criteria);

		Model model = new ExtendedModelMap();
		model.addAttribute("resultSize", customerManager.querySize(criteria));
		model.addAttribute("customerList", customerManager.query(criteria, page, size, sortColumn, order));
		model.addAttribute("customerTypeList", lookupManager.getAllCustomerType(request.getLocale()));
		return new ModelAndView("customerList", model.asMap());
	}

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale locale = request.getLocale();
		String[] checkbox = request.getParameterValues("checkbox");
		if (null != checkbox && checkbox.length > 0) {
			for (int i = 0; i < checkbox.length; i++) {
				Customer invItem = customerManager.get(Long.valueOf(checkbox[i]));
				customerManager.remove(Long.valueOf(checkbox[i]));
				saveMessage(request, getText("invItem.deleted", invItem.getName(), locale));
			}
		} else {
			saveError(request, "global.errorNoCheckboxSelectForDelete");
		}
		return new ModelAndView("redirect:/customerList");
	}

}
