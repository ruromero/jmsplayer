package com.guadalcode.tools.service.jms.impl;

import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.guadalcode.tools.model.jms.DestinationConfig;
import com.guadalcode.tools.model.jms.MessageContent;
import com.guadalcode.tools.service.jms.JMSSenderService;

public class WeblogicJMSSenderService implements JMSSenderService {

    private static final String WL_INITIAL_CONTEXT_FACTORY = "weblogic.jndi.WLInitialContextFactory";
    private static final Logger logger = LogManager.getLogger(WeblogicJMSSenderService.class);
    
    @Override
    public void send(DestinationConfig destinationCfg, MessageContent message) {
	InitialContext ctx = null;
	Hashtable<String, String> properties = new Hashtable<>();
	properties.put(Context.INITIAL_CONTEXT_FACTORY, WL_INITIAL_CONTEXT_FACTORY);
	properties.put(Context.PROVIDER_URL, destinationCfg.getEndpoint());
	properties.put(Context.SECURITY_PRINCIPAL, destinationCfg.getUsername());
	properties.put(Context.SECURITY_CREDENTIALS, destinationCfg.getPassword());
	try {
	    ctx = new InitialContext(properties);
	    logger.debug("Created initial context for destination: {}", destinationCfg.getName());
	} catch (NamingException e) {
	    logger.error("Unable to create the initial context", e);
	}
	if(ctx != null) {
	    try {
		ConnectionFactory connectionFactory = (ConnectionFactory)ctx.lookup(destinationCfg.getConnectionFactory());
		Connection conn = connectionFactory.createConnection();
		Session session = conn.createSession(false, 0);
		Destination destination = (Destination)ctx.lookup(destinationCfg.getDestinationName());
		MessageProducer producer = session.createProducer(destination);
		Message msg = session.createTextMessage(message.getText());
		if(!Strings.isBlank(message.getType())) {
		    msg.setJMSType(message.getType());
		}
		logger.debug("Message configured and ready to be sent");
		producer.send(msg);
		logger.debug("Message successfully sent");
	    } catch (NamingException | JMSException e) {
		logger.error("Unable to create the JMS Connection to " + destinationCfg.getConnectionFactory() + " - " + destinationCfg.getDestinationName(), e);
	    }
	}

    }

}
