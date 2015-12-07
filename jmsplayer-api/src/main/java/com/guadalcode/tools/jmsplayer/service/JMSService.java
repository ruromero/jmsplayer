package com.guadalcode.tools.jmsplayer.service;

<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
=======
import javax.inject.Inject;
>>>>>>> b8a7aa6d8441f107d9f71abdd264861108195bd7

import com.guadalcode.tools.jmsplayer.model.DestinationConfig;
import com.guadalcode.tools.jmsplayer.model.MessageContent;
import com.guadalcode.tools.jmsplayer.service.configuration.ConfigurationService;

/**
 * @author rromero
 *
 */
@Service
public class JMSService {

<<<<<<< HEAD
	@Autowired
    private ConfigurationService configurationService;
    
	@Autowired
=======
    @Inject
    private ConfigurationService configurationService;
    
    @Inject
>>>>>>> b8a7aa6d8441f107d9f71abdd264861108195bd7
    private JMSProducer producer;

    public void sendMessage(String destinationName, MessageContent message) {
        DestinationConfig destination = configurationService.get(destinationName);
        if (destination == null) {
            throw new IllegalArgumentException("The requested destination does not exist");
        }
        producer.send(destination, message);
    }

}
