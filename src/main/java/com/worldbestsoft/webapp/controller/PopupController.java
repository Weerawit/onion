package com.worldbestsoft.webapp.controller;

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
import com.worldbestsoft.model.Employee;
import com.worldbestsoft.model.InvItem;
import com.worldbestsoft.model.InvItemGroup;
import com.worldbestsoft.service.CustomerManager;
import com.worldbestsoft.service.EmployeeManager;
import com.worldbestsoft.service.InvItemGroupManager;
import com.worldbestsoft.service.InvItemManager;
import com.worldbestsoft.service.LookupManager;

@Controller
@RequestMapping("/popup*")
public class PopupController extends BaseFormController {

	private InvItemManager invItemManager;
	private InvItemGroupManager invItemGroupManager;
	private EmployeeManager employeeManager;
	private CustomerManager customerManager;
	private LookupManager lookupManager;

	public InvItemManager getInvItemManager() {
		return invItemManager;
	}

	@Autowired
	public void setInvItemManager(InvItemManager invItemManager) {
		this.invItemManager = invItemManager;
	}

	public InvItemGroupManager getInvItemGroupManager() {
		return invItemGroupManager;
	}

	@Autowired
	public void setInvItemGroupManager(InvItemGroupManager invItemGroupManager) {
		this.invItemGroupManager = invItemGroupManager;
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

	public LookupManager getLookupManager() {
		return lookupManager;
	}

	@Autowired
	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}

	@RequestMapping(value="/item*", method = RequestMethod.GET)
	public ModelAndView listItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		InvItem criteria = new InvItem();
		criteria.setCode(request.getParameter("code"));
		criteria.setName(request.getParameter("name"));
		InvItemGroup invItemGroup = new InvItemGroup();
		invItemGroup.setCode(request.getParameter("invItem.invItemGroup.code"));
		criteria.setInvItemGroup(invItemGroup);
		int page = getPage(request);
		String sortColumn = getSortColumn(request);
		String order = getSortOrder(request);
		int size = getPageSize(request);

		log.info(request.getRemoteUser() + " is quering InvItem criteria := " + criteria);

		Model model = new ExtendedModelMap();
		model.addAttribute("resultSize", invItemManager.querySize(criteria));
		model.addAttribute("invItemList", invItemManager.query(criteria, page, size, sortColumn, order));
		model.addAttribute("invItemGroupList", invItemGroupManager.getAllInvItemGroup());
		return new ModelAndView("popup/itemList", model.asMap());
	}
	
	@RequestMapping(value="/employee*", method = RequestMethod.GET)
	public ModelAndView listEmployee(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Employee criteria = new Employee();
		criteria.setFirstName(request.getParameter("firstName"));
		criteria.setLastName(request.getParameter("lastName"));
		criteria.setNickName(request.getParameter("nickName"));
		int page = getPage(request);
		String sortColumn = getSortColumn(request);
		String order = getSortOrder(request);
		int size = getPageSize(request);

		log.info(request.getRemoteUser() + " is quering Employee criteria := " + criteria);

		Model model = new ExtendedModelMap();
		model.addAttribute("resultSize", employeeManager.querySize(criteria));
		model.addAttribute("employeeList", employeeManager.query(criteria, page, size, sortColumn, order));
		return new ModelAndView("popup/employeeList", model.asMap());
	}
	
	@RequestMapping(value="/customer*", method = RequestMethod.GET)
	public ModelAndView listCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		return new ModelAndView("popup/customerList", model.asMap());
	}
}
