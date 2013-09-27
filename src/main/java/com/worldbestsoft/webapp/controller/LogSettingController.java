package com.worldbestsoft.webapp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.worldbestsoft.webapp.controller.BaseFormController;

@Controller
@RequestMapping("/admin/logSetting*")
public class LogSettingController extends BaseFormController {
	private final Log log = LogFactory.getLog(getClass());
	
	private static List<String> levels = Arrays.asList(new String[] {"TRACE", "DEBUG",  "INFO", "WARN", "ERROR", "FATAL", "OFF"});
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String logName = request.getParameter("logName");
		Enumeration<Logger> currentLoggers =  LogManager.getCurrentLoggers();
		List<Logger> logList = new ArrayList<Logger>();
		
		if (StringUtils.isBlank(logName) || StringUtils.startsWithIgnoreCase("root", logName)) {
			logList.add(LogManager.getRootLogger());
		}
		while (currentLoggers.hasMoreElements()) {
			Logger currentLogger = currentLoggers.nextElement();
			if (StringUtils.isBlank(logName) || StringUtils.startsWithIgnoreCase(currentLogger.getName(), logName)) {
				logList.add(currentLogger);
			}
		}
		Model model = new ExtendedModelMap();
		model.addAttribute("logList", logList);
		model.addAttribute("levels", levels);
		return new ModelAndView("admin/logSetting", model.asMap());
	}
	
	@RequestMapping(method = {RequestMethod.POST})
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] loggers = request.getParameterValues("log");
		String[] levels = request.getParameterValues("level");
		for (int i = 0; i < loggers.length; i++) {
			String logger = loggers[i];
			String level = levels[i];
			Logger loggerObj = null;
			if (StringUtils.equalsIgnoreCase(logger, "root")) {
				loggerObj = LogManager.getRootLogger();
			} else {
				loggerObj = LogManager.getLogger(logger);
			}
			Level levelObj = null;
			if (StringUtils.isNotBlank(level)) {
				levelObj = Level.toLevel(level);
			}
			if (!ObjectUtils.equals(loggerObj.getLevel(), levelObj)) {
				loggerObj.setLevel(Level.toLevel(level));
			}
		}
		return new ModelAndView("redirect:/admin/logSetting");
	}
}
