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

import com.worldbestsoft.model.Catalog;
import com.worldbestsoft.model.CatalogType;
import com.worldbestsoft.service.CatalogManager;
import com.worldbestsoft.service.CatalogTypeManager;

@Controller
@RequestMapping("/catalogList*")
public class CatalogListController extends BaseFormController {

	private CatalogManager catalogManager;
	private CatalogTypeManager catalogTypeManager;
	
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

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		return new ModelAndView("catalogList", model.asMap());
	}

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale locale = request.getLocale();
		String[] checkbox = request.getParameterValues("checkbox");
		if (null != checkbox && checkbox.length > 0) {
			for (int i = 0; i < checkbox.length; i++) {
				Catalog catalog = catalogManager.get(Long.valueOf(checkbox[i]));
				catalogManager.remove(Long.valueOf(checkbox[i]));
				saveMessage(request, getText("catalog.deleted", catalog.getCode(), locale));
			}
		} else {
			saveError(request, "global.errorNoCheckboxSelectForDelete");
		}
		return new ModelAndView("redirect:/catalogList");
	}

}
