package com.guadalcode.tools.jmsplayer.service;

import javax.inject.Inject;

import com.guadalcode.tools.jmsplayer.model.DestinationConfig;
import com.guadalcode.tools.jmsplayer.model.MessageContent;
import com.guadalcode.tools.jmsplayer.service.configuration.ConfigurationService;

/**
 * @author rromero
 *
 */
public class JMSService {

    @Inject
    private ConfigurationService configurationService;
    
    @Inject
    private JMSProducer producer;

    public void sendMessage(String destinationName, MessageContent message) {
        DestinationConfig destination = configurationService.get(destinationName);
        if (destination == null) {
            throw new IllegalArgumentException("The requested destination does not exist");
        }
        producer.send(destination, message);
    }

}
