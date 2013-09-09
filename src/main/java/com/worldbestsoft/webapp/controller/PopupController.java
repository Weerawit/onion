package com.worldbestsoft.webapp.controller;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.worldbestsoft.model.Catalog;
import com.worldbestsoft.model.CatalogType;
import com.worldbestsoft.model.Customer;
import com.worldbestsoft.model.DocumentNumber;
import com.worldbestsoft.model.Employee;
import com.worldbestsoft.model.InvItem;
import com.worldbestsoft.model.InvItemGroup;
import com.worldbestsoft.model.Supplier;
import com.worldbestsoft.model.criteria.SaleOrderCriteria;
import com.worldbestsoft.service.CatalogManager;
import com.worldbestsoft.service.CatalogTypeManager;
import com.worldbestsoft.service.CustomerManager;
import com.worldbestsoft.service.EmployeeManager;
import com.worldbestsoft.service.InvItemGroupManager;
import com.worldbestsoft.service.InvItemManager;
import com.worldbestsoft.service.LookupManager;
import com.worldbestsoft.service.SaleOrderManager;
import com.worldbestsoft.service.SupplierManager;

@Controller
@RequestMapping("/popup*")
public class PopupController extends BaseFormController {
	
	private static final String[] DATE_PATTERN = new String[] {"dd/MM/yyyy HH:mm:ss", "dd/MM/yyyy"};

	private InvItemManager invItemManager;
	private InvItemGroupManager invItemGroupManager;
	private EmployeeManager employeeManager;
	private CustomerManager customerManager;
	private LookupManager lookupManager;
	private CatalogManager catalogManager;
	private CatalogTypeManager catalogTypeManager;
	private SaleOrderManager saleOrderManager;
	private SupplierManager supplierManager;
	
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
	
	public CatalogManager getCatalogManager() {
		return catalogManager;
	}

	@Autowired
	public void setCatalogManager(CatalogManager catalogManager) {
		this.catalogManager = catalogManager;
	}
	
	public CatalogTypeManager getCatalogTypeManager() {
		return catalogTypeManager;
	}

	@Autowired
	public void setCatalogTypeManager(CatalogTypeManager catalogTypeManager) {
		this.catalogTypeManager = catalogTypeManager;
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
	public ModelAndView listItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		InvItem criteria = new InvItem();
		criteria.setCode(request.getParameter("code"));
		criteria.setName(request.getParameter("name"));
		InvItemGroup invItemGroup = new InvItemGroup();
		invItemGroup.setCode(request.getParameter("invItemGroup.code"));
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
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
			criteria.setId(Long.valueOf(id));
		}
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
	
	@RequestMapping(value="/supplier*", method = RequestMethod.GET)
	public ModelAndView listSupplier(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Supplier criteria = new Supplier();
		criteria.setCode(request.getParameter("code"));
		criteria.setName(request.getParameter("name"));
		criteria.setContactName(request.getParameter("contactName"));
		criteria.setContactTel(request.getParameter("contactTel"));
		int page = getPage(request);
		String sortColumn = getSortColumn(request);
		String order = getSortOrder(request);
		int size = getPageSize(request);

		log.info(request.getRemoteUser() + " is quering Supplier criteria := " + criteria);

		Model model = new ExtendedModelMap();
		model.addAttribute("resultSize", supplierManager.querySize(criteria));
		model.addAttribute("supplierList", supplierManager.query(criteria, page, size, sortColumn, order));
		return new ModelAndView("popup/supplierList", model.asMap());
	}
	
	@RequestMapping(value="/catalog*", method = RequestMethod.GET)
	public ModelAndView listCatalog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Catalog criteria = new Catalog();
		criteria.setCode(request.getParameter("code"));
		criteria.setName(request.getParameter("name"));
		CatalogType catalogType = new CatalogType();
		catalogType.setCode(request.getParameter("catalogType.code"));
		criteria.setCatalogType(catalogType);
		int page = getPage(request);
		String sortColumn = getSortColumn(request);
		String order = getSortOrder(request);
		int size = getPageSize(request);

		log.info(request.getRemoteUser() + " is quering Catalog criteria := " + criteria);

		Model model = new ExtendedModelMap();
		model.addAttribute("resultSize", catalogManager.querySize(criteria));
		model.addAttribute("catalogList", catalogManager.query(criteria, page, size, sortColumn, order));
		model.addAttribute("catalogTypeList", catalogTypeManager.getAllCatalogType());
		return new ModelAndView("popup/catalogList", model.asMap());
	}
	
	@RequestMapping(value="/saleOrder*", method = RequestMethod.GET)
	public ModelAndView listSaleOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Model model = new ExtendedModelMap();
		model.addAttribute("saleOrderStatusList", lookupManager.getAllSaleOrderStatusList(request.getLocale()));
		SaleOrderCriteria criteria = new SaleOrderCriteria();
		DocumentNumber documentNumber = new DocumentNumber();
		documentNumber.setDocumentNo(request.getParameter("documentNumber.documentNo"));
		criteria.setDocumentNumber(documentNumber);
		
		criteria.setStatus(request.getParameter("status"));
		
		String startTime = request.getParameter("deliveryDateFrom");
		String endTime = request.getParameter("deliveryDateTo");
		try {
			if (StringUtils.isNotBlank(startTime)) {
				Date startTimeDate = DateUtils.parseDateStrictly(startTime, DATE_PATTERN);
				criteria.setDeliveryDateFrom(startTimeDate);
			}
		} catch (ParseException e) {
			saveError(request, getText("errors.date", new Object[] { startTime }, request.getLocale()));
			return new ModelAndView("popup/saleOrderList", model.asMap());
		}
		try {
			if (StringUtils.isNotBlank(endTime)) {
				Date endTimeDate = DateUtils.parseDate(endTime, DATE_PATTERN);
				criteria.setDeliveryDateTo(endTimeDate);
			}
		} catch (ParseException e) {
			saveError(request, getText("errors.date", new Object[] { endTime }, request.getLocale()));
			return new ModelAndView("popup/saleOrderList", model.asMap());
		}
		
		Customer customer = new Customer();
		customer.setName(request.getParameter("customer.name"));
		criteria.setCustomer(customer);
		int page = getPage(request);
		String sortColumn = getSortColumn(request);
		String order = getSortOrder(request);
		int size = getPageSize(request);

		log.info(request.getRemoteUser() + " is quering SaleOrder criteria := " + criteria);

		model.addAttribute("resultSize", saleOrderManager.querySize(criteria));
		model.addAttribute("saleOrderList", saleOrderManager.query(criteria, page, size, sortColumn, order));
		return new ModelAndView("popup/saleOrderList", model.asMap());
	}
}
