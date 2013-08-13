package com.worldbestsoft.webapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.worldbestsoft.model.InvGoodsReceipt;
import com.worldbestsoft.model.CatalogItem;
import com.worldbestsoft.model.InvItem;
import com.worldbestsoft.model.criteria.CatalogForm;
import com.worldbestsoft.service.CatalogItemManager;
import com.worldbestsoft.service.InvItemManager;

@Controller
@RequestMapping("/catalogItem*")
public class CatalogItemFormController extends BaseFormController {
	
	private CatalogItemManager catalogItemManager;
	private InvItemManager invItemManager;
	
	public CatalogItemManager getCatalogItemManager() {
		return catalogItemManager;
	}

	@Autowired
	public void setCatalogItemManager(CatalogItemManager catalogItemManager) {
		this.catalogItemManager = catalogItemManager;
	}

	public InvItemManager getInvItemManager() {
		return invItemManager;
	}

	@Autowired
	public void setInvItemManager(InvItemManager invItemManager) {
		this.invItemManager = invItemManager;
	}

    @RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(CatalogItem catalogItemForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("cancel") != null) {
			return new ModelAndView("redirect:/catalog");
		}
		
		Locale locale = request.getLocale();
		HttpSession session = request.getSession();
		
		CatalogForm catalog = (CatalogForm) session.getAttribute("catalogForm");
		Set<CatalogItem> catalogItemList = catalog.getCatalogItems();

		String rowNum = request.getParameter("rowNum");
		
		if (validator != null) { // validator is null during testing
			validator.validate(catalogItemForm, errors);
			if (null != catalogItemForm.getInvItem()) {
				InvItem invItem = getInvItemManager().findByInvItemCode(catalogItemForm.getInvItem().getCode());
				catalogItemForm.setInvItem(invItem);
				if (null == invItem) {
					errors.rejectValue("invItem.code", "errors.invalid",new Object[] { getText("catalogItem.invItem.code", request.getLocale())}, "errors.invalid");	
				}
			}
			//validate only for add mode (rowNum is empty)
			if (StringUtils.isBlank(rowNum)) {
				//check dup from session
				CatalogItem catalogItem = (CatalogItem) CollectionUtils.find(catalogItemList, new BeanPropertyValueEqualsPredicate("invItem.code", catalogItemForm.getInvItem().getCode()));
				if (null != catalogItem) {
					errors.rejectValue("invItem.code", "catalogItem.duplicate",new Object[] { getText("catalogItem.invItem.code", request.getLocale())}, "catalogItem.duplicate");
				}
			}
			
			if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting
				return new ModelAndView("catalogItem", "catalogItem", catalogItemForm);
			}
		}
		log.info(request.getRemoteUser() + " is saving CatalogItem := " + catalogItemForm);

		if (request.getParameter("delete") != null) {
			//remove from session list.
			CatalogItem catalogItem = (CatalogItem) CollectionUtils.find(catalogItemList, new BeanPropertyValueEqualsPredicate("invItem.code", catalogItemForm.getInvItem().getCode()));
			catalogItemList.remove(catalogItem);
			
			saveMessage(request, getText("catalogItem.deleted", catalogItemForm.getInvItem().getCode(), locale));
			return new ModelAndView("redirect:/catalog");
		} else {
			
			
			if (StringUtils.isBlank(rowNum)) {
				//add to list
				
				catalogItemList.add(catalogItemForm);
				
				saveMessage(request, getText("catalogItem.added", catalogItemForm.getInvItem().getCode(), locale));
				return new ModelAndView("redirect:/catalog");
			} else {
				//update old object
				List<CatalogItem> catalogItemList2 = new ArrayList<CatalogItem>(catalog.getCatalogItems());
				try {
					CatalogItem catalogItem = catalogItemList2.get(Integer.parseInt(rowNum));
					
					catalogItem.setQty(catalogItemForm.getQty());
					catalogItem.setName(catalogItemForm.getName());
					
					saveMessage(request, getText("catalogItem.saved", catalogItemForm.getInvItem().getCode(), locale));
					return new ModelAndView("redirect:/catalog");
					
				} catch (Exception e) {
					saveError(request, getText("errors.invalid", rowNum, locale));
					return new ModelAndView("redirect:/catalog?from=list");
				} 
				
			}
		}
	}
	
	@RequestMapping(method = { RequestMethod.GET })
	protected ModelAndView display(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		
		//parameter 'id' is index of array list from session object
		HttpSession session = request.getSession();
		CatalogForm catalog = (CatalogForm) session.getAttribute("catalogForm");
		if (null != catalog) {
			List<CatalogItem> catalogItemList = new ArrayList<CatalogItem>(catalog.getCatalogItems());
			CatalogItem catalogItem = new CatalogItem();
			try {
				Integer index = Integer.parseInt(id);
				if (index < catalogItemList.size()) {
					catalogItem = catalogItemList.get(index);
				}
			} catch (Exception e) {
				//no handle exception; any exception mean new object.
			}
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("catalogItem", catalogItem);
			return new ModelAndView("catalogItem", model);
		} else {
			//since no object in session, redirect to Catalog page
			return new ModelAndView("redirect:/catalog");
		}
	}

}
