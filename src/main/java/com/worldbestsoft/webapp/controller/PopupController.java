package com.worldbestsoft.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.worldbestsoft.model.InvItem;
import com.worldbestsoft.model.InvItemGroup;
import com.worldbestsoft.service.InvItemGroupManager;
import com.worldbestsoft.service.InvItemManager;

@Controller
@RequestMapping("/popup*")
public class PopupController extends BaseFormController {

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

	@RequestMapping(value="/item*", method = RequestMethod.GET)
	public ModelAndView listItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		InvItem criteria = new InvItem();
		criteria.setCode(request.getParameter("code"));
		criteria.setName(request.getParameter("name"));
		InvItemGroup invItemGroup = new InvItemGroup();
		invItemGroup.setCode(request.getParameter("invItem.invItemGroup.code"));
		criteria.setInvItemGroup(invItemGroup);
		int page = getPage(request);
		String sortColumn = getSortColumn(request);
		String order = getSortOrder(request);
		int size = getPageSize(request);

		log.info(request.getRemoteUser() + " is quering InvItem criteria := " + criteria);

		Model model = new ExtendedModelMap();
		model.addAttribute("resultSize", invItemManager.querySize(criteria));
		model.addAttribute("invItemList", invItemManager.query(criteria, page, size, sortColumn, order));
		model.addAttribute("invItemGroupList", invItemGroupManager.getAllInvItemGroup());
		return new ModelAndView("popup/itemList", model.asMap());
	}
}