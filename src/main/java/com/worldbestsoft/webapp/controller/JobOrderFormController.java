package com.worldbestsoft.webapp.controller;

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

import com.worldbestsoft.model.Catalog;
import com.worldbestsoft.model.Employee;
import com.worldbestsoft.model.JobOrder;
import com.worldbestsoft.model.SaleOrder;
import com.worldbestsoft.service.CatalogManager;
import com.worldbestsoft.service.EmployeeManager;
import com.worldbestsoft.service.JobOrderManager;
import com.worldbestsoft.service.LookupManager;
import com.worldbestsoft.service.SaleOrderManager;

@Controller
@RequestMapping("/jobOrder*")
public class JobOrderFormController extends BaseFormController {
	
	private LookupManager lookupManager;
	private JobOrderManager jobOrderManager;
	private EmployeeManager employeeManager;
	private SaleOrderManager saleOrderManager;
	private CatalogManager catalogManager;
	
	public LookupManager getLookupManager() {
		return lookupManager;
	}

	@Autowired
	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}

	public JobOrderManager getJobOrderManager() {
		return jobOrderManager;
	}

	@Autowired
	public void setJobOrderManager(JobOrderManager jobOrderManager) {
		this.jobOrderManager = jobOrderManager;
	}
	
	public EmployeeManager getEmployeeManager() {
		return employeeManager;
	}

	@Autowired
	public void setEmployeeManager(EmployeeManager employeeManager) {
		this.employeeManager = employeeManager;
	}

	public SaleOrderManager getSaleOrderManager() {
		return saleOrderManager;
	}

	@Autowired
	public void setSaleOrderManager(SaleOrderManager saleOrderManager) {
		this.saleOrderManager = saleOrderManager;
	}
	
	public CatalogManager getCatalogManager() {
		return catalogManager;
	}

	@Autowired
	public void setCatalogManager(CatalogManager catalogManager) {
		this.catalogManager = catalogManager;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(JobOrder jobOrderForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("cancel") != null) {
			return new ModelAndView("redirect:/jobOrderList");
		}

		if (validator != null) { // validator is null during testing
			validator.validate(jobOrderForm, errors);
			
			if (null != jobOrderForm.getSaleOrder()) {
				SaleOrder saleOrder = getSaleOrderManager().findBySaleOrderNo(jobOrderForm.getSaleOrder().getSaleOrderNo());
				jobOrderForm.setSaleOrder(saleOrder);
				if (null == saleOrder) {
					errors.rejectValue("saleOrder.saleOrderNo", "errors.invalid", new Object[] { getText("jobOrder.saleOrder.saleOrderNo", request.getLocale())}, "errors.invalid");	
				}
			}
			
			if (null != jobOrderForm.getCatalog()) {
				Catalog catalog = getCatalogManager().findByCatalogCode(jobOrderForm.getCatalog().getCode());
				jobOrderForm.setCatalog(catalog);
				if (null == catalog) {
					errors.rejectValue("catalog.name", "errors.invalid", new Object[] { getText("jobOrder.catalog.name", request.getLocale())}, "errors.invalid");	
				}
			}
			
			if (null != jobOrderForm.getEmployee()) {
				Employee employee = getEmployeeManager().get(jobOrderForm.getEmployee().getId());
				jobOrderForm.setEmployee(employee);
				if (null == employee) {
					errors.rejectValue("jobOrder.employee.id", "errors.invalid", new Object[] { getText("jobOrder.employee.id", request.getLocale())}, "errors.invalid");	
				}
			}
			
			if (errors.hasErrors() && !StringUtils.equalsIgnoreCase("delete", request.getParameter("action"))) { // don't validate when deleting
				return new ModelAndView("jobOrder", "jobOrder", jobOrderForm).addObject("jobOrderStatusList", lookupManager.getAllJobOrderStatus(request.getLocale()));
			}
		}
		log.info(request.getRemoteUser() + " is saving JobOrder := " + jobOrderForm);

		Locale locale = request.getLocale();

		if (StringUtils.equalsIgnoreCase("delete", request.getParameter("action"))) {
			//since code input is readonly, no value pass to form then we need to query from db.
//			JobOrder jobOrder = getJobOrderManager().get(jobOrderForm.getId());
			getJobOrderManager().remove(jobOrderForm.getId());
			saveMessage(request, getText("jobOrder.deleted", jobOrderForm.getRunningNo(), locale));
			return new ModelAndView("redirect:/jobOrderList");
		} else {
			//edit
			JobOrder jobOrder = getJobOrderManager().get(jobOrderForm.getId());
			jobOrder.setStatus(jobOrderForm.getStatus());
			jobOrder.setActualEndDate(jobOrderForm.getActualEndDate());
			jobOrder.setEmployee(jobOrderForm.getEmployee());
			jobOrder.setQty(jobOrderForm.getQty());
			jobOrder.setCost(jobOrderForm.getCost());
			jobOrder.setStatus(jobOrderForm.getStatus());
			jobOrder.setSaleOrder(jobOrderForm.getSaleOrder());
			jobOrder = getJobOrderManager().save(jobOrder);

			request.setAttribute("jobOrder", jobOrder);
			saveMessage(request, getText("jobOrder.saved", jobOrder.getRunningNo(), locale));
			return new ModelAndView("redirect:/jobOrderList");
		}
	}

	@RequestMapping(method = { RequestMethod.GET })
	protected ModelAndView display(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		JobOrder jobOrder = new JobOrder();
		if (!isFormSubmission(request)) {
			if (id != null) {
				jobOrder = getJobOrderManager().get(Long.valueOf(id));
			}
		} else {
			jobOrder = getJobOrderManager().get(Long.valueOf(id));
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("jobOrder", jobOrder);
		model.put("jobOrderStatusList", lookupManager.getAllJobOrderStatus(request.getLocale()));
		return new ModelAndView("jobOrder", model);
	}
	
	@RequestMapping(value = "/displayTable")
	protected ModelAndView displayTable(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String catalogCode = request.getParameter("catalog.code");
		
		Catalog catalog = getCatalogManager().findByCatalogCode(catalogCode);
		Map<String, Object> model = new HashMap<String, Object>();
		if (null != catalog) { 
			model.put("catalogItemList", catalog.getCatalogItems());
		}
		return new ModelAndView("jobOrderTable", model);
	}

	private boolean isFormSubmission(HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("post");
	}

}
