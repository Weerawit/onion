package com.worldbestsoft.webapp.util;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class Log4jStreamAppender extends AppenderSkeleton {
	
	private SimpMessagingTemplate messagingTemplate;

	public SimpMessagingTemplate getMessagingTemplate() {
		return messagingTemplate;
	}

	@Autowired
	public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
		messagingTemplate.setMessageConverter(new StringMessageConverter());
		this.messagingTemplate = messagingTemplate;
	}

	@Override
    public void close() {
	    
    }

	@Override
    public boolean requiresLayout() {
	    return true;
    }

	@Override
    protected void append(LoggingEvent event) {
		if (null != getLayout()) {
			Layout layout = getLayout();
			String msg = layout.format(event);
			messagingTemplate.convertAndSend("/topic/logStream", msg);
		}
    }

}
