package com.guadalcode.tools.jmsplayer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guadalcode.tools.jmsplayer.model.DestinationConfig;
import com.guadalcode.tools.jmsplayer.model.MessageContent;
import com.guadalcode.tools.jmsplayer.service.configuration.ConfigurationService;

/**
 * @author rromero
 *
 */
@Service
public class JMSService {

	@Autowired
    private ConfigurationService configurationService;
    
	@Autowired
    private JMSProducer producer;

    public void sendMessage(String destinationName, MessageContent message) {
        DestinationConfig destination = configurationService.get(destinationName);
        if (destination == null) {
            throw new IllegalArgumentException("The requested destination does not exist");
        }
        producer.send(destination, message);
    }

}
