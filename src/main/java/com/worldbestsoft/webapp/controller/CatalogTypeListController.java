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

import com.worldbestsoft.model.CatalogType;
import com.worldbestsoft.service.CatalogTypeManager;

@Controller
@RequestMapping("/catalogTypeList*")
public class CatalogTypeListController extends BaseFormController {

	private CatalogTypeManager catalogTypeManager;

	public CatalogTypeManager getCatalogTypeManager() {
		return catalogTypeManager;
	}

	@Autowired
	public void setCatalogTypeManager(CatalogTypeManager catalogTypeManager) {
		this.catalogTypeManager = catalogTypeManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CatalogType criteria = new CatalogType();
		criteria.setCode(request.getParameter("code"));
		criteria.setName(request.getParameter("name"));
		int page = getPage(request);
		String sortColumn = getSortColumn(request);
		String order = getSortOrder(request);
		int size = getPageSize(request);

		log.info(request.getRemoteUser() + " is quering CatalogType criteria := " + criteria);

		Model model = new ExtendedModelMap();
		model.addAttribute("resultSize", catalogTypeManager.querySize(criteria));
		model.addAttribute("catalogTypeList", catalogTypeManager.query(criteria, page, size, sortColumn, order));
		return new ModelAndView("catalogTypeList", model.asMap());
	}

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale locale = request.getLocale();
		String[] checkbox = request.getParameterValues("checkbox");
		if (null != checkbox && checkbox.length > 0) {
			for (int i = 0; i < checkbox.length; i++) {
				CatalogType catalogType = catalogTypeManager.get(Long.valueOf(checkbox[i]));
				catalogTypeManager.remove(Long.valueOf(checkbox[i]));
				saveMessage(request, getText("catalogType.deleted", catalogType.getCode(), locale));
			}
		} else {
			saveError(request, "global.errorNoCheckboxSelectForDelete");
		}
		return new ModelAndView("redirect:/catalogTypeList");
	}

}
