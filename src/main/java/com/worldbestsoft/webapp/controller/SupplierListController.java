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

import com.worldbestsoft.model.Supplier;
import com.worldbestsoft.service.SupplierManager;

@Controller
@RequestMapping("/supplierList*")
public class SupplierListController extends BaseFormController {

	private SupplierManager supplierManager;
	
	public SupplierManager getSupplierManager() {
		return supplierManager;
	}

	@Autowired
	public void setSupplierManager(SupplierManager supplierManager) {
		this.supplierManager = supplierManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		return new ModelAndView("supplierList", model.asMap());
	}

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale locale = request.getLocale();
		String[] checkbox = request.getParameterValues("checkbox");
		if (null != checkbox && checkbox.length > 0) {
			for (int i = 0; i < checkbox.length; i++) {
				Supplier supplier = supplierManager.get(Long.valueOf(checkbox[i]));
				supplierManager.remove(Long.valueOf(checkbox[i]));
				saveMessage(request, getText("supplier.deleted", supplier.getCode(), locale));
			}
		} else {
			saveError(request, getText("global.errorNoCheckboxSelectForDelete", request.getLocale()));
		}
		return new ModelAndView("redirect:/supplierList");
	}

}
