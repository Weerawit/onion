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

import com.worldbestsoft.model.Employee;
import com.worldbestsoft.service.EmployeeManager;

@Controller
@RequestMapping("/employeeList*")
public class EmployeeListController extends BaseFormController {

	private EmployeeManager employeeManager;
	
	public EmployeeManager getEmployeeManager() {
		return employeeManager;
	}

	@Autowired
	public void setEmployeeManager(EmployeeManager employeeManager) {
		this.employeeManager = employeeManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		return new ModelAndView("employeeList", model.asMap());
	}

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale locale = request.getLocale();
		String[] checkbox = request.getParameterValues("checkbox");
		if (null != checkbox && checkbox.length > 0) {
			for (int i = 0; i < checkbox.length; i++) {
				Employee employee = employeeManager.get(Long.valueOf(checkbox[i]));
				employeeManager.remove(Long.valueOf(checkbox[i]));
				saveMessage(request, getText("employee.deleted", employee.getId(), locale));
			}
		} else {
			saveError(request, getText("global.errorNoCheckboxSelectForDelete", request.getLocale()));
		}
		return new ModelAndView("redirect:/employeeList");
	}

}
