package com.guadalcode.tools.jmsplayer.service;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.guadalcode.tools.jmsplayer.model.DestinationConfig;
import com.guadalcode.tools.jmsplayer.model.JMSProviderType;
import com.guadalcode.tools.jmsplayer.model.MessageContent;
import com.guadalcode.tools.jmsplayer.util.consumer.JMSConsumer;
import com.guadalcode.tools.jmsplayer.util.consumer.JMSConsumerFactory;

public class WeblogicJMSProducerIntegration {

    private static JMSService service = JMSService.getInstance();
    private static DestinationConfig config;
    private static JMSConsumer consumer;
    
    @BeforeClass
    public static void setUp() {
	config = new DestinationConfig();
	config.setName("Default WL Queue");
	config.setEndpoint("t3://localhost:7001");
	config.setProviderType(JMSProviderType.WEBLOGIC);
	config.setUsername("weblogic");
	config.setPassword("weblogic1");
	config.setConnectionFactory("jms/defaultConnectionFactory");
	config.setDestinationName("jms/myqueue");
	
	service.addDestination(config);
	
	consumer = JMSConsumerFactory.build(config);
	consumer.start();
	
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
	consumer.stop();
    }
    
    @After
    public void clean() {
	consumer.clear();
    }
    
    @Test
    public void testBasicSend() throws Exception {
	MessageContent message = new MessageContent("Hello world");

	service.sendMessage(config.getName(), message);
	
	Thread.sleep(1000);
	
	assertEquals(1, consumer.getMessages().size());
	consumer.assertReceived(message);
    }
    
    @Test
    public void testBasicSendWithType() throws Exception {
	MessageContent message = new MessageContent("Hello world", "My JMS Type");
	
	service.sendMessage(config.getName(), message);
	
	Thread.sleep(1000);
	
	assertEquals(1, consumer.getMessages().size());
	consumer.assertReceived(message);
    }
    
    @Test
    public void testMultipleSend() throws Exception {
	MessageContent message = new MessageContent("Hello world", "My JMS Type");
	
	service.sendMessage(config.getName(), message);
	service.sendMessage(config.getName(), message);
	service.sendMessage(config.getName(), message);
	service.sendMessage(config.getName(), message);
	service.sendMessage(config.getName(), message);
	
	Thread.sleep(1000);
	
	assertEquals(5, consumer.getMessages().size());
	consumer.assertReceived(message);
    }
}
