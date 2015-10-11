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
import com.guadalcode.tools.jmsplayer.util.consumer.JMSConsumerImpl;
import com.guadalcode.tools.jmsplayer.util.server.ActiveMQServer;

/**
 * @author rromero
 *
 */
public class EmbeddedActiveMQJMSProducerIntegration {

    private static DestinationConfig config;
    private static JMSService service = JMSService.getInstance();
    private static JMSConsumer consumer;
    private static ActiveMQServer server = new ActiveMQServer();

    @BeforeClass
    public static void setUp() throws Exception {
        server.start();

        config = new DestinationConfig();
        config.setName("Default ActiveMQ Queue");
        config.setConnectionFactory("ConnectionFactory");
        config.setEndpoint("tcp://localhost:61616");
        config.setProviderType(JMSProviderType.EMBEDDED_ACTIVEMQ);
        config.setDestinationName("dynamicQueues/FOO.BAR");

        service.addDestination(config);

        consumer = new JMSConsumerImpl(config);
        consumer.start();

    }

    @AfterClass
    public static void tearDown() throws Exception {
        consumer.stop();

        server.stop();
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
