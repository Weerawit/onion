package com.worldbestsoft.webapp.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.worldbestsoft.model.Catalog;
import com.worldbestsoft.util.DownloadToken;

@Controller
@RequestMapping("/download*")
public class DownloadController {
	private DownloadToken downloadToken;

	public DownloadToken getDownloadToken() {
		return downloadToken;
	}

	@Autowired
	public void setDownloadToken(DownloadToken downloadToken) {
		this.downloadToken = downloadToken;
	}
	
	@RequestMapping(value="/dialog")
	public ModelAndView dialog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Model model = new ExtendedModelMap();
		
		return new ModelAndView("downloadDialog", model.asMap());
	}
	@RequestMapping(value="/progress")
	public @ResponseBody Map<String, String> progresss(@RequestParam("token") String token) {
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("success", "true");
		responseMap.put("token", downloadToken.check(token));
		return responseMap;
	}
	
	@RequestMapping(value="/token")
	public @ResponseBody Map<String, String> token() {
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("success", "true");
		responseMap.put("token", downloadToken.generate());
		return responseMap;
	}
}
