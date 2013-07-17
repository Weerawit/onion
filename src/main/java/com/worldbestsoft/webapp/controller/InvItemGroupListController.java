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

import com.worldbestsoft.model.InvItemGroup;
import com.worldbestsoft.service.InvItemGroupManager;

@Controller
@RequestMapping("/invItemGroupList*")
public class InvItemGroupListController extends BaseFormController {

	private InvItemGroupManager invItemGroupManager;

	public InvItemGroupManager getInvItemGroupManager() {
		return invItemGroupManager;
	}

	@Autowired
	public void setInvItemGroupManager(InvItemGroupManager invItemGroupManager) {
		this.invItemGroupManager = invItemGroupManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		InvItemGroup criteria = new InvItemGroup();
		criteria.setCode(request.getParameter("code"));
		criteria.setName(request.getParameter("name"));
		int page = getPage(request);
		String sortColumn = getSortColumn(request);
		String order = getSortOrder(request);
		int size = getPageSize(request);

		log.info(request.getRemoteUser() + " is quering InvItemGroup criteria := " + criteria);

		Model model = new ExtendedModelMap();
		model.addAttribute("resultSize", invItemGroupManager.querySize(criteria));
		model.addAttribute("invItemGroupList", invItemGroupManager.query(criteria, page, size, sortColumn, order));
		return new ModelAndView("invItemGroupList", model.asMap());
	}

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale locale = request.getLocale();
		String[] checkbox = request.getParameterValues("checkbox");
		if (null != checkbox && checkbox.length > 0) {
			for (int i = 0; i < checkbox.length; i++) {
				InvItemGroup invItemGroup = invItemGroupManager.get(Long.valueOf(checkbox[i]));
				invItemGroupManager.remove(Long.valueOf(checkbox[i]));
				saveMessage(request, getText("invItemGroup.deleted", invItemGroup.getCode(), locale));
			}
		} else {
			saveError(request, "global.errorNoCheckboxSelectForDelete");
		}
		return new ModelAndView("redirect:/invItemGroupList");
	}

}
