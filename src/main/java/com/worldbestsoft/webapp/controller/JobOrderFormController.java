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

import com.worldbestsoft.model.JobOrder;
import com.worldbestsoft.service.JobOrderManager;
import com.worldbestsoft.service.LookupManager;

@Controller
@RequestMapping("/jobOrder*")
public class JobOrderFormController extends BaseFormController {
	
	private LookupManager lookupManager;
	private JobOrderManager jobOrderManager;
	
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

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(JobOrder jobOrderForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("cancel") != null) {
			return new ModelAndView("redirect:/jobOrderList");
		}

		if (validator != null) { // validator is null during testing
			validator.validate(jobOrderForm, errors);
			
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
			saveMessage(request, getText("jobOrder.deleted", jobOrderForm.getId(), locale));
			return new ModelAndView("redirect:/jobOrderList");
		} else {
			//edit
			JobOrder jobOrder = getJobOrderManager().get(jobOrderForm.getId());
			jobOrder.setStatus(jobOrderForm.getStatus());
			jobOrder.setActualEndDate(jobOrderForm.getActualEndDate());
			jobOrder.setEmployee(jobOrderForm.getEmployee());
			jobOrder = getJobOrderManager().save(jobOrder);

			request.setAttribute("jobOrder", jobOrder);
			saveMessage(request, getText("jobOrder.saved", jobOrder.getId(), locale));
			return new ModelAndView("redirect:/jobOrderList");
		}
	}

	@RequestMapping(method = { RequestMethod.GET })
	protected ModelAndView display(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			//no id, mean go back to list page
			return new ModelAndView("redirect:/jobOrderList");
		}
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
		model.put("catalogItemList", jobOrder.getSaleOrderItem().getCatalog().getCatalogItems());
		return new ModelAndView("jobOrder", model);
	}

	private boolean isFormSubmission(HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("post");
	}

}
