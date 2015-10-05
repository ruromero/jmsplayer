package com.guadalcode.tools.jmsplayer.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.guadalcode.tools.jmsplayer.model.DestinationConfig;
import com.guadalcode.tools.jmsplayer.model.JMSProviderType;
import com.guadalcode.tools.jmsplayer.model.MessageContent;
import com.guadalcode.tools.jmsplayer.service.impl.ActiveMQJMSSenderService;
import com.guadalcode.tools.jmsplayer.service.impl.WeblogicJMSSenderService;

public class JMSService {

    private Map<String, DestinationConfig> destinations = new HashMap<>();
    
    private Map<JMSProviderType, JMSSenderService> senderServices = new HashMap<>();
    
    public JMSService() {
	senderServices.put(JMSProviderType.WEBLOGIC, new WeblogicJMSSenderService());
	senderServices.put(JMSProviderType.ACTIVEMQ, new ActiveMQJMSSenderService());
    }
    
    public Collection<DestinationConfig> getDestinations() {
	return destinations.values();
    }
    
    public Collection<DestinationConfig> addDestination(DestinationConfig destination) {
	if(destination == null) {
	    throw new IllegalArgumentException("Destination must not be null");
	}
	if(destination.getName() == null) {
	    throw new IllegalArgumentException("Destination name must not be null");
	}
	destinations.put(destination.getName(), destination);
	return destinations.values();
    }
    
    public Collection<DestinationConfig> removeDestination(String destinationName) {
	if(destinationName == null) {
	    throw new IllegalArgumentException("Destination name must not be null");
	}
	destinations.remove(destinationName);
	return destinations.values();
    }
    
    public void sendMessage(String destinationName, MessageContent message) {
	DestinationConfig destination = destinations.get(destinationName);
	if(destination == null) {
	    throw new IllegalArgumentException("The requested destination does not exist");
	}
	JMSSenderService sender = senderServices.get(destination.getProviderType());
	if(sender == null) {
	    throw new UnsupportedOperationException("Unable to find a JMS Sender of type: " + destination.getProviderType());
	}
	sender.send(destination, message);
    }
    
}
