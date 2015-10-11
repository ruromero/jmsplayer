package com.guadalcode.tools.jmsplayer.service;

import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.guadalcode.tools.jmsplayer.model.DestinationConfig;
import com.guadalcode.tools.jmsplayer.model.MessageContent;

public class JMSProducerImpl implements JMSProducer {

    private static final Logger logger = LogManager.getLogger(JMSProducerImpl.class);

    @Override
    public void send(DestinationConfig destinationCfg, MessageContent message) {
        InitialContext ctx = null;
        Hashtable<String, String> properties = new Hashtable<>();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, destinationCfg.getProviderType().getInitialContextFactory());
        properties.put(Context.PROVIDER_URL, destinationCfg.getEndpoint());
        properties.put(Context.SECURITY_PRINCIPAL, destinationCfg.getUsername());
        properties.put(Context.SECURITY_CREDENTIALS, destinationCfg.getPassword());
        try {
            ctx = new InitialContext(properties);
            logger.debug("Created initial context for destination: {}", destinationCfg.getName());
        } catch (NamingException e) {
            logger.error("Unable to create the initial context", e);
        }
        if (ctx != null) {
            Connection conn = null;
            Session session = null;
            MessageProducer producer = null;
            try {
                ConnectionFactory connectionFactory = (ConnectionFactory) ctx.lookup(destinationCfg.getConnectionFactory());
                conn = connectionFactory.createConnection();
                session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Destination destination = (Destination) ctx.lookup(destinationCfg.getDestinationName());
                producer = session.createProducer(destination);
                TextMessage msg = session.createTextMessage(message.getText());
                if (!Strings.isBlank(message.getType())) {
                    msg.setJMSType(message.getType());
                }
                logger.debug("Message configured and ready to be sent");
                producer.send(msg);
                logger.debug("Message successfully sent");
            } catch (NamingException | JMSException e) {
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

}
