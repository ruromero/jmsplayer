package com.guadalcode.tools.service.jms;

import org.junit.Test;

import com.guadalcode.tools.model.jms.DestinationConfig;
import com.guadalcode.tools.model.jms.JMSProviderType;
import com.guadalcode.tools.model.jms.MessageContent;

public class WeblogicJMSSenderServiceIntegration {

    @Test
    public void testBasicSend() {
	JMSService service = new JMSService();
	DestinationConfig config = new DestinationConfig("Default WL Queue");
	config.setEndpoint("t3://localhost:7001");
	config.setProviderType(JMSProviderType.WEBLOGIC);
	config.setUsername("weblogic");
	config.setPassword("weblogic1");
	config.setConnectionFactory("jms/defaultConnectionFactory");
	config.setDestinationName("jms/myqueue");
	service.addDestination(config);
	
	MessageContent message = new MessageContent();
	message.setText("Hello world");
	service.sendMessage(config.getName(), message);
    }
}
