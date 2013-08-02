package com.worldbestsoft.webapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.worldbestsoft.model.InvItem;
import com.worldbestsoft.service.InvItemManager;

@Controller
@RequestMapping("/json*")
public class JsonController {
	
	private InvItemManager invItemManager;
	
	public InvItemManager getInvItemManager() {
		return invItemManager;
	}

	@Autowired
	public void setInvItemManager(InvItemManager invItemManager) {
		this.invItemManager = invItemManager;
	}

	@RequestMapping(value="/item*", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, String>> getInvItemList(@RequestParam("q") String name) {
		InvItem criteria = new InvItem();
		criteria.setName(name);
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		List<InvItem> invItemList = invItemManager.query(criteria, 0, 10, null, null);
		for (InvItem invItem : invItemList) {
			Map<String, String> model = new HashMap<String, String>();
			model.put("name", invItem.getName());
			model.put("code", invItem.getCode());
			resultList.add(model);
		}
		return resultList;
	}

}
