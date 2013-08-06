package com.worldbestsoft.webapp.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.worldbestsoft.model.Supplier;
import com.worldbestsoft.service.SupplierManager;

@Controller
@RequestMapping("/supplier*")
public class SupplierFormController extends BaseFormController {
	
	private SupplierManager supplierManager;
	
	public SupplierManager getSupplierManager() {
		return supplierManager;
	}

	@Autowired
	public void setSupplierManager(SupplierManager supplierManager) {
		this.supplierManager = supplierManager;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(Supplier supplierForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("cancel") != null) {
			return new ModelAndView("redirect:/supplierList");
		}

		if (validator != null) { // validator is null during testing
			validator.validate(supplierForm, errors);
			
			if (null == supplierForm.getId()) {
				List<Supplier> dupList = getSupplierManager().checkDuplicate(supplierForm.getCode(), null);
				if (null != dupList && dupList.size() > 0) {
					errors.rejectValue("code", "supplier.duplicate",new Object[] { getText("supplier.code", request.getLocale())}, "supplier.duplicate");
				}
			}

			if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting
				return new ModelAndView("supplier", "supplier", supplierForm);
			}
		}
		log.info(request.getRemoteUser() + " is saving Supplier := " + supplierForm);

		Locale locale = request.getLocale();

		if (request.getParameter("delete") != null) {
			//since code input is readonly, no value pass to form then we need to query from db.
//			Supplier supplier = getSupplierManager().get(supplierForm.getId());
			getSupplierManager().remove(supplierForm.getId());
			saveMessage(request, getText("supplier.deleted", supplierForm.getCode(), locale));
			return new ModelAndView("redirect:/supplierList");
		} else {
			
			if (null == supplierForm.getId()) {
				// add
				supplierForm.setCreateDate(new Date());
				supplierForm.setCreateUser(request.getRemoteUser());
				
				supplierForm = getSupplierManager().save(supplierForm);

				saveMessage(request, getText("supplier.added", supplierForm.getCode(), locale));
				return new ModelAndView("redirect:/supplier").addObject("id", supplierForm.getId());
			} else {
				// edit

				Supplier supplier = getSupplierManager().get(supplierForm.getId());
				supplier.setName(supplierForm.getName());
				supplier.setAddress(supplierForm.getAddress());
				supplier.setContactName(supplierForm.getContactName());
				supplier.setContactTel(supplierForm.getContactTel());
				supplier.setUpdateDate(new Date());
				supplier.setUpdateUser(request.getRemoteUser());
				supplier = getSupplierManager().save(supplier);

				request.setAttribute("supplier", supplier);
				saveMessage(request, getText("supplier.saved", supplier.getCode(), locale));
				return new ModelAndView("redirect:/supplierList");
			}
		}
	}

	@RequestMapping(method = { RequestMethod.GET })
	protected ModelAndView display(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Supplier supplier = new Supplier();
		if (!isFormSubmission(request)) {
			if (id != null) {
				supplier = getSupplierManager().get(Long.valueOf(id));
			}
		} else {
			supplier = getSupplierManager().get(Long.valueOf(id));
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("supplier", supplier);
		return new ModelAndView("supplier", model);
	}

	private boolean isFormSubmission(HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("post");
	}

}
