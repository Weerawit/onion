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

import com.worldbestsoft.Constants;
import com.worldbestsoft.dao.SearchException;
import com.worldbestsoft.model.User;
import com.worldbestsoft.service.UserManager;

/**
 * Simple class to retrieve a list of users from the database.
 * <p/>
 * <p>
 * <a href="UserController.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
@RequestMapping("/admin/users*")
public class UserController extends BaseFormController {
	private UserManager userManager = null;

	@Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User criteria = new User();
		criteria.setUsername(request.getParameter("username"));
		log.info(request.getRemoteUser() + " is quering user criteria := " + criteria);
		int page = getPage(request);
		String sortColumn = getSortColumn(request);
		String order = getSortOrder(request);
		int size = 25;

		Model model = new ExtendedModelMap();
		try {
			model.addAttribute("resultSize", userManager.querySize(criteria));
			model.addAttribute(Constants.USER_LIST, userManager.query(criteria, page, size, sortColumn, order));
		} catch (SearchException se) {
			model.addAttribute("searchError", se.getMessage());
			model.addAttribute(userManager.getUsers());
		}
		return new ModelAndView("admin/userList", model.asMap());
	}

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale locale = request.getLocale();
		String[] checkbox = request.getParameterValues("checkbox");
		if (null != checkbox && checkbox.length > 0) {
			for (int i = 0; i < checkbox.length; i++) {
				User user = userManager.get(Long.valueOf(checkbox[i]));
				userManager.remove(Long.valueOf(checkbox[i]));
				saveMessage(request, getText("user.deleted", user.getUsername(), locale));
			}
		} else {
			saveError(request, getText("global.errorNoCheckboxSelectForDelete", request.getLocale()));
		}
		return new ModelAndView("redirect:/admin/users");
	}
}
