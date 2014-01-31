package com.worldbestsoft.webapp.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.CountDownLatch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Appender;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogViewController {
	
	private final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping("/admin/logView*")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/logView");
	}
	
//	@RequestMapping(value="/admin/logView/stream*")
//	@ResponseBody
//	public void onRequest(final AtmosphereResource resource) throws IOException {
////		resource.getResponse().setContentType("text/plain;charset=UTF-8");
////		this.broadcaster.addAtmosphereResource(resource);
////		resource.setBroadcaster(this.broadcaster);
//		final CountDownLatch countDownLatch = new CountDownLatch(1);
//		resource.addEventListener(new AtmosphereResourceEventListenerAdapter() {
//			@Override
//			public void onSuspend(AtmosphereResourceEvent event) {
//				countDownLatch.countDown();
////				log.info("Suspending Client..." + resource.uuid());
//				resource.removeEventListener(this);
//			}
//
//			@Override
//			public void onDisconnect(AtmosphereResourceEvent event) {
////				log.info("Disconnecting Client..." + resource.uuid());
//				super.onDisconnect(event);
//			}
//
//			@Override
//			public void onBroadcast(AtmosphereResourceEvent event) {
////				log.info("Client is broadcasting..." + resource.uuid());
//				super.onBroadcast(event);
//			}
//
//		});
//		getLogAppender().getBroadcaster().addAtmosphereResource(resource);
//		
//		
//		if (AtmosphereResource.TRANSPORT.LONG_POLLING.equals(resource.transport())) {
//			resource.resumeOnBroadcast(true).suspend(-1, false);
//		} else {
//			resource.suspend(-1);
//		}
//
//		try {
//			countDownLatch.await();
//		} catch (InterruptedException e) {
////			log.error("Interrupted while trying to suspend resource " + resource, e);
//		}
//	}
}
