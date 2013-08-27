package com.worldbestsoft.webapp.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.worldbestsoft.model.CatalogType;
import com.worldbestsoft.service.CatalogTypeManager;

@Controller
@RequestMapping("/catalogType*")
public class CatalogTypeFormController extends BaseFormController {
	
	private CatalogTypeManager catalogTypeManager;
	
	public CatalogTypeManager getCatalogTypeManager() {
		return catalogTypeManager;
	}

	@Autowired
	public void setCatalogTypeManager(CatalogTypeManager catalogTypeManager) {
		this.catalogTypeManager = catalogTypeManager;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(CatalogType catalogTypeForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("cancel") != null) {
			return new ModelAndView("redirect:/catalogTypeList");
		}

		if (validator != null) { // validator is null during testing
			validator.validate(catalogTypeForm, errors);
			
			if (null == catalogTypeForm.getId()) {
				List<CatalogType> dupList = getCatalogTypeManager().checkDuplicate(catalogTypeForm.getCode(), null);
				if (null != dupList && dupList.size() > 0) {
					errors.rejectValue("code", "catalogType.duplicate",new Object[] { getText("catalogType.code", request.getLocale())}, "catalogType.duplicate");
				}
			}

			if (errors.hasErrors() && StringUtils.equalsIgnoreCase("delete", request.getParameter("action"))) { // don't validate when deleting
				return new ModelAndView("catalogType", "catalogType", catalogTypeForm);
			}
		}
		log.info(request.getRemoteUser() + " is saving CatalogType := " + catalogTypeForm);

		Locale locale = request.getLocale();

		if (StringUtils.equalsIgnoreCase("delete", request.getParameter("action"))) {
			//since code input is readonly, no value pass to form then we need to query from db.
//			CatalogType catalogType = getCatalogTypeManager().get(catalogTypeForm.getId());
			getCatalogTypeManager().remove(catalogTypeForm.getId());
			saveMessage(request, getText("catalogType.deleted", catalogTypeForm.getCode(), locale));
			return new ModelAndView("redirect:/catalogTypeList");
		} else {
			

			if (null == catalogTypeForm.getId()) {
				// add
				catalogTypeForm.setCreateDate(new Date());
				catalogTypeForm.setCreateUser(request.getRemoteUser());
				
				catalogTypeForm = getCatalogTypeManager().save(catalogTypeForm);

				saveMessage(request, getText("catalogType.added", catalogTypeForm.getCode(), locale));
				return new ModelAndView("redirect:/catalogType").addObject("id", catalogTypeForm.getId());
			} else {
				// edit

				CatalogType catalogType = getCatalogTypeManager().get(catalogTypeForm.getId());
				catalogType.setName(catalogTypeForm.getName());
				catalogType.setUpdateDate(new Date());
				catalogType.setUpdateUser(request.getRemoteUser());
				catalogType = getCatalogTypeManager().save(catalogType);

				request.setAttribute("catalogType", catalogType);
				saveMessage(request, getText("catalogType.saved", catalogType.getCode(), locale));
				return new ModelAndView("redirect:/catalogTypeList");
			}

		}

	}

	@RequestMapping(method = { RequestMethod.GET })
	protected ModelAndView display(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		CatalogType catalogType = new CatalogType();
		if (!isFormSubmission(request)) {
			if (id != null) {
				catalogType = getCatalogTypeManager().get(Long.valueOf(id));
			}
		} else {
			catalogType = getCatalogTypeManager().get(Long.valueOf(id));
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("catalogType", catalogType);
		return new ModelAndView("catalogType", model);
	}

	private boolean isFormSubmission(HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("post");
	}

}
