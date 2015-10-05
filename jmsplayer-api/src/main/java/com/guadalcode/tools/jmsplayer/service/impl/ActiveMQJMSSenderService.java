package com.guadalcode.tools.jmsplayer.service.impl;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.guadalcode.tools.jmsplayer.model.DestinationConfig;
import com.guadalcode.tools.jmsplayer.model.MessageContent;
import com.guadalcode.tools.jmsplayer.service.JMSSenderService;

public class ActiveMQJMSSenderService implements JMSSenderService {

    private static final Logger logger = LogManager.getLogger(ActiveMQJMSSenderService.class);

    @Override
    public void send(DestinationConfig destinationCfg, MessageContent message) {
	Connection conn = null;
	Session session = null;
	MessageProducer producer = null;
	try {
	    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(destinationCfg.getConnectionFactory());
	    conn = connectionFactory.createConnection();
	    session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
	    Destination destination = (Destination) session.createQueue(destinationCfg.getEndpoint());
	    producer = session.createProducer(destination);
	    TextMessage msg = session.createTextMessage(message.getText());
	    if (!Strings.isBlank(message.getType())) {
		msg.setJMSType(message.getType());
	    }
	    logger.debug("Message configured and ready to be sent");
	    producer.send(msg);
	    logger.debug("Message successfully sent");
	} catch (JMSException e) {
	    logger.error("Unable to create the JMS Connection to " + destinationCfg.getConnectionFactory() + " - "
		    + destinationCfg.getDestinationName(), e);
	} finally {
	    try {
		if (producer != null) {
		    producer.close();
		}
		if (session != null) {
		    session.close();
		}
		if (conn != null) {
		    conn.close();
		}
	    } catch (JMSException e1) {
		logger.error("Unable to close the resources", e1);
	    }
	}

    }

}
