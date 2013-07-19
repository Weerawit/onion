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

import com.worldbestsoft.model.InvItem;
import com.worldbestsoft.model.InvItemGroup;
import com.worldbestsoft.service.InvItemGroupManager;
import com.worldbestsoft.service.InvItemManager;

@Controller
@RequestMapping("/invItem*")
public class InvItemFormController extends BaseFormController {
	
	private InvItemManager invItemManager;
	private InvItemGroupManager invItemGroupManager;
	
	public InvItemManager getInvItemManager() {
		return invItemManager;
	}

	@Autowired
	public void setInvItemManager(InvItemManager invItemManager) {
		this.invItemManager = invItemManager;
	}

	public InvItemGroupManager getInvItemGroupManager() {
		return invItemGroupManager;
	}

	@Autowired
	public void setInvItemGroupManager(InvItemGroupManager invItemGroupManager) {
		this.invItemGroupManager = invItemGroupManager;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(InvItem invItemForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("cancel") != null) {
			return new ModelAndView("redirect:/invItemList");
		}

		if (validator != null) { // validator is null during testing
			validator.validate(invItemForm, errors);

			if ((errors.hasErrors() && request.getParameter("delete") == null) && (errors.hasErrors() && request.getParameter("id") == null)) { // don't
				// validate
				// when
				// deleting
				return new ModelAndView("invItem", "invItem", invItemForm);
			}
		}
		log.info(request.getRemoteUser() + " is saving InvItem := " + invItemForm);

		Locale locale = request.getLocale();

		if (request.getParameter("delete") != null) {
			//since code input is readonly, no value pass to form then we need to query from db.
//			InvItem invItem = getInvItemManager().get(invItemForm.getId());
			getInvItemManager().remove(invItemForm.getId());
			saveMessage(request, getText("invItem.deleted", invItemForm.getCode(), locale));
			return new ModelAndView("redirect:/invItemList");
		} else {
			
			if (null == invItemForm.getId()) {
				// add
				invItemForm.setCreateDate(new Date());
				invItemForm.setCreateUser(request.getRemoteUser());
				List<InvItem> dupList = getInvItemManager().checkDuplicate(invItemForm.getCode(), null);
				if (null != dupList && dupList.size() > 0) {
					saveError(request, getText("invItem.duplicate", invItemForm.getCode(), locale));
					return new ModelAndView("invItem", "invItem", invItemForm);
				}
				
				if (null != invItemForm.getInvItemGroup()) {
					InvItemGroup invItemGroup = getInvItemGroupManager().findByInvItemGroupCode(invItemForm.getInvItemGroup().getCode());
					invItemForm.setInvItemGroup(invItemGroup);
				}

				invItemForm = getInvItemManager().save(invItemForm);

				saveMessage(request, getText("invItem.added", invItemForm.getCode(), locale));
				return new ModelAndView("redirect:/invItem").addObject("id", invItemForm.getId());
			} else {
				// edit

				InvItem invItem = getInvItemManager().get(invItemForm.getId());
				invItem.setName(invItemForm.getName());
				if (null != invItemForm.getInvItemGroup()) {
					InvItemGroup invItemGroup = getInvItemGroupManager().findByInvItemGroupCode(invItemForm.getInvItemGroup().getCode());
					invItem.setInvItemGroup(invItemGroup);
				}
				invItem.setDescription(invItemForm.getDescription());
				invItem.setUpdateDate(new Date());
				invItem.setUpdateUser(request.getRemoteUser());
				invItem = getInvItemManager().save(invItem);

				request.setAttribute("invItem", invItem);
				saveMessage(request, getText("invItem.saved", invItem.getCode(), locale));
				return new ModelAndView("redirect:/invItemList");
			}
		}
	}

	@RequestMapping(method = { RequestMethod.GET })
	protected ModelAndView display(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		InvItem invItem = new InvItem();
		if (!isFormSubmission(request)) {
			if (id != null) {
				invItem = getInvItemManager().get(Long.valueOf(id));
			}
		} else {
			invItem = getInvItemManager().get(Long.valueOf(id));
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("invItem", invItem);
		model.put("invItemGroupList", invItemGroupManager.getAllInvItemGroup());
		return new ModelAndView("invItem", model);
	}

	private boolean isFormSubmission(HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("post");
	}

}
