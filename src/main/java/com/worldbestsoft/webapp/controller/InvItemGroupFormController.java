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

import com.worldbestsoft.model.InvItemGroup;
import com.worldbestsoft.service.InvItemGroupManager;

@Controller
@RequestMapping("/invItemGroup*")
public class InvItemGroupFormController extends BaseFormController {
	
	private InvItemGroupManager invItemGroupManager;
	
	public InvItemGroupManager getInvItemGroupManager() {
		return invItemGroupManager;
	}

	@Autowired
	public void setInvItemGroupManager(InvItemGroupManager invItemGroupManager) {
		this.invItemGroupManager = invItemGroupManager;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(InvItemGroup invItemGroupForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("cancel") != null) {
			return new ModelAndView("redirect:/invItemGroupList");
		}

		if (validator != null) { // validator is null during testing
			validator.validate(invItemGroupForm, errors);
			
			if (null == invItemGroupForm.getId()) {
				List<InvItemGroup> dupList = getInvItemGroupManager().checkDuplicate(invItemGroupForm.getCode(), null);
				if (null != dupList && dupList.size() > 0) {
					errors.rejectValue("code", "invItemGroup.duplicate",new Object[] { getText("invItemGroup.code", request.getLocale())}, "invItemGroup.duplicate");
				}
			}

			if (errors.hasErrors() && !StringUtils.equalsIgnoreCase("delete", request.getParameter("action"))) { // don't validate when deleting
				return new ModelAndView("invItemGroup", "invItemGroup", invItemGroupForm);
			}
		}
		log.info(request.getRemoteUser() + " is saving InvItemGroup := " + invItemGroupForm);

		Locale locale = request.getLocale();

		if (StringUtils.equalsIgnoreCase("delete", request.getParameter("action"))) {
			//since code input is readonly, no value pass to form then we need to query from db.
//			InvItemGroup invItemGroup = getInvItemGroupManager().get(invItemGroupForm.getId());
			getInvItemGroupManager().remove(invItemGroupForm.getId());
			saveMessage(request, getText("invItemGroup.deleted", invItemGroupForm.getCode(), locale));
			return new ModelAndView("redirect:/invItemGroupList");
		} else {
			

			if (null == invItemGroupForm.getId()) {
				// add
				invItemGroupForm.setCreateDate(new Date());
				invItemGroupForm.setCreateUser(request.getRemoteUser());
				
				invItemGroupForm = getInvItemGroupManager().save(invItemGroupForm);

				saveMessage(request, getText("invItemGroup.added", invItemGroupForm.getCode(), locale));
				return new ModelAndView("redirect:/invItemGroup").addObject("id", invItemGroupForm.getId());
			} else {
				// edit

				InvItemGroup invItemGroup = getInvItemGroupManager().get(invItemGroupForm.getId());
				invItemGroup.setName(invItemGroupForm.getName());
				invItemGroup.setUpdateDate(new Date());
				invItemGroup.setUpdateUser(request.getRemoteUser());
				invItemGroup = getInvItemGroupManager().save(invItemGroup);

				request.setAttribute("invItemGroup", invItemGroup);
				saveMessage(request, getText("invItemGroup.saved", invItemGroup.getCode(), locale));
				return new ModelAndView("redirect:/invItemGroupList");
			}

		}

	}

	@RequestMapping(method = { RequestMethod.GET })
	protected ModelAndView display(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		InvItemGroup invItemGroup = new InvItemGroup();
		if (!isFormSubmission(request)) {
			if (id != null) {
				invItemGroup = getInvItemGroupManager().get(Long.valueOf(id));
			}
		} else {
			invItemGroup = getInvItemGroupManager().get(Long.valueOf(id));
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("invItemGroup", invItemGroup);
		return new ModelAndView("invItemGroup", model);
	}

	private boolean isFormSubmission(HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("post");
	}

}
