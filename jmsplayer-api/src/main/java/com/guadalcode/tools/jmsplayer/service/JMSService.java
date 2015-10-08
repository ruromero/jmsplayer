package com.guadalcode.tools.jmsplayer.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Strings;
import com.guadalcode.tools.jmsplayer.model.DestinationConfig;
import com.guadalcode.tools.jmsplayer.model.JMSProviderType;
import com.guadalcode.tools.jmsplayer.model.MessageContent;
import com.guadalcode.tools.jmsplayer.service.impl.EmbeddedActiveMQJMSProducer;
import com.guadalcode.tools.jmsplayer.service.impl.WeblogicJMSProducer;

/**
 * TODO: Use any IoC mecanism to instantiate this and take care of all the
 * concurrency threats
 * 
 * @author rromero
 *
 */
public class JMSService {

    private Map<String, DestinationConfig> destinations = Collections.synchronizedMap(new HashMap<String, DestinationConfig>());

    private Map<JMSProviderType, JMSProducer> producers = Collections.synchronizedMap(new HashMap<JMSProviderType, JMSProducer>());

    private static JMSService INSTANCE = new JMSService();

    private JMSService() {
        producers.put(JMSProviderType.WEBLOGIC, new WeblogicJMSProducer());
        producers.put(JMSProviderType.EMBEDDED_ACTIVEMQ, new EmbeddedActiveMQJMSProducer());
    }

    public Collection<DestinationConfig> getDestinations() {
        return destinations.values();
    }

    public Collection<DestinationConfig> addDestination(DestinationConfig destination) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination must not be null");
        }
        if (destination.getName() == null) {
            throw new IllegalArgumentException("Destination name must not be null");
        }
        destinations.put(destination.getName(), destination);
        return destinations.values();
    }

    public Collection<DestinationConfig> removeDestination(String destinationName) {
        if (destinationName == null) {
            throw new IllegalArgumentException("Destination name must not be null");
        }
        destinations.remove(destinationName);
        return destinations.values();
    }

    public void sendMessage(String destinationName, MessageContent message) {
        DestinationConfig destination = destinations.get(destinationName);
        if (destination == null) {
            throw new IllegalArgumentException("The requested destination does not exist");
        }
        JMSProducer sender = producers.get(destination.getProviderType());
        if (sender == null) {
            throw new UnsupportedOperationException("Unable to find a JMS Sender of type: "
                    + destination.getProviderType());
        }
        sender.send(destination, message);
    }

    public DestinationConfig getDestination(String name) {
        if (Strings.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("The name must not be null or empty");
        }
        return destinations.get(name);
    }

    public void updateDestination(String name, DestinationConfig config) {
        if (Strings.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("The name must not be null or empty");
        }
        if (destinations.containsKey(name)) {
            destinations.put(name, config);
        }
    }

    public static JMSService getInstance() {
        return INSTANCE;
    }

}
