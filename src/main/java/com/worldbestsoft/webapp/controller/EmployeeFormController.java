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

import com.worldbestsoft.model.Employee;
import com.worldbestsoft.service.EmployeeManager;

@Controller
@RequestMapping("/employee*")
public class EmployeeFormController extends BaseFormController {
	
	private EmployeeManager employeeManager;
	

	public EmployeeManager getEmployeeManager() {
		return employeeManager;
	}

	@Autowired
	public void setEmployeeManager(EmployeeManager employeeManager) {
		this.employeeManager = employeeManager;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(Employee employeeForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("cancel") != null) {
			return new ModelAndView("redirect:/employeeList");
		}

		if (validator != null) { // validator is null during testing
			validator.validate(employeeForm, errors);

			if (errors.hasErrors() && StringUtils.equalsIgnoreCase("delete", request.getParameter("action"))) { // don't validate when deleting
				// validate
				// when
				// deleting
				return new ModelAndView("employee", "employee", employeeForm);
			}
		}
		log.info(request.getRemoteUser() + " is saving Employee := " + employeeForm);

		Locale locale = request.getLocale();

		if (StringUtils.equalsIgnoreCase("delete", request.getParameter("action"))) {
			//since code input is readonly, no value pass to form then we need to query from db.
//			Employee employee = getEmployeeManager().get(employeeForm.getId());
			getEmployeeManager().remove(employeeForm.getId());
			saveMessage(request, getText("employee.deleted", employeeForm.getId().toString(), locale));
			return new ModelAndView("redirect:/employeeList");
		} else {
			

			if (null == employeeForm.getId()) {
				// add

				employeeForm = getEmployeeManager().save(employeeForm);

				saveMessage(request, getText("employee.added", employeeForm.getId().toString(), locale));
				return new ModelAndView("redirect:/employee").addObject("id", employeeForm.getId());
			} else {
				// edit

				Employee employee = getEmployeeManager().get(employeeForm.getId());
				employee.setFirstName(employeeForm.getFirstName());
				employee.setLastName(employeeForm.getLastName());
				employee.setNickName(employeeForm.getNickName());
				employee.setAge(employeeForm.getAge());
				employee.setIdCardNo(employeeForm.getIdCardNo());
				employee.setAddress(employeeForm.getAddress());
				employee.setWage(employeeForm.getWage());
				employee.setMemo(employeeForm.getMemo());
				employee = getEmployeeManager().save(employee);

				request.setAttribute("employee", employee);
				saveMessage(request, getText("employee.saved", employee.getId().toString(), locale));
				return new ModelAndView("redirect:/employeeList");
			}

		}

	}

	@RequestMapping(method = { RequestMethod.GET })
	protected ModelAndView display(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Employee employee = new Employee();
		if (!isFormSubmission(request)) {
			if (id != null) {
				employee = getEmployeeManager().get(Long.valueOf(id));
			}
		} else {
			employee = getEmployeeManager().get(Long.valueOf(id));
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("employee", employee);
		return new ModelAndView("employee", model);
	}

	private boolean isFormSubmission(HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("post");
	}

}
