package com.guadalcode.tools.jmsplayer.util.consumer.impl;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.guadalcode.tools.jmsplayer.model.DestinationConfig;
import com.guadalcode.tools.jmsplayer.model.MessageContent;
import com.guadalcode.tools.jmsplayer.util.consumer.JMSConsumer;

public class ActiveMQJMSConsumer implements JMSConsumer {

    private static final Logger logger = LogManager.getLogger(ActiveMQJMSConsumer.class);
    private static final long MAX_WAIT = 1000L;

    private List<MessageContent> messages = new ArrayList<>();

    private DestinationConfig config;

    private boolean active = false;

    public ActiveMQJMSConsumer(DestinationConfig config) {
	this.config = config;
    }

    @Override
    public void start() {
	new Thread() {
	    @Override
	    public synchronized void run() {
		Connection conn = null;
		Session session = null;
		MessageConsumer consumer = null;
		try {
		    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(config.getEndpoint());
		    conn = connectionFactory.createConnection();
		    conn.start();
		    session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		    Destination destination = (Destination) session.createQueue(config.getDestinationName());
		    consumer = session.createConsumer(destination);
		    active = true;
		    logger.debug("Consumer listening to incoming messages");
		    while (active) {
			Message message = consumer.receive();
			if (message != null && message instanceof TextMessage) {
			    String text = ((TextMessage) message).getText();
			    MessageContent content = new MessageContent(text, message.getJMSType());
			    messages.add(content);
			    logger.debug("Message consumed: {}", content);
			} else {
			    if (message != null) {
				logger.warn("Received incorrect type of message");
			    }
			    logger.debug("Max wait {} ms reached but no text message found", MAX_WAIT);
			}
		    }

		} catch (JMSException e) {
		    logger.error("Unable to create the JMS Connection to " + config.getConnectionFactory() + " - "
			    + config.getDestinationName(), e);
		} finally {
		    try {
			if (conn != null) {
			    conn.close();
			}
			if (session != null) {
			    session.close();
			}
			if (consumer != null) {
			    consumer.close();
			}
		    } catch (JMSException e1) {
			logger.error("Unable to close the resources", e1);
		    }
		}
	    }

	}.start();

    }

    @Override
    public void stop() throws Exception {
	active = false;
	logger.debug("Stopping message consumer");
	Thread.sleep(MAX_WAIT);
    }

    @Override
    public List<MessageContent> getMessages() {
	return messages;
    }

    @Override
    public void clear() {
	messages.clear();
    }

    @Override
    public int size() {
	return messages.size();
    }

    @Override
    public void assertReceived(MessageContent expected) {
	for (MessageContent message : messages) {
	    if (message.equals(expected)) {
		return;
	    }
	}
	fail("Expected message not received: " + expected);
    }

    @Override
    public void assertNotReceived(MessageContent expected) {
	for (MessageContent message : messages) {
	    if (message.equals(expected)) {
		fail("Received message but not expected: " + expected);
	    }
	}
    }

}
