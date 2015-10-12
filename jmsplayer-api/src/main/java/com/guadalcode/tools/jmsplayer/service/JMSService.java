package com.guadalcode.tools.jmsplayer.service;

import com.guadalcode.tools.jmsplayer.model.DestinationConfig;
import com.guadalcode.tools.jmsplayer.model.MessageContent;
import com.guadalcode.tools.jmsplayer.service.configuration.ConfigurationService;

/**
 * @author rromero
 *
 */
public class JMSService {

    //eager loading
    private static JMSService INSTANCE = new JMSService();
    
    private ConfigurationService configurationService = ConfigurationService.getInstance();
    
    private JMSProducer producer = new JMSProducerImpl();

    private JMSService() {
    }
    
    public static JMSService getInstance() {
        return INSTANCE;
    }

    public void sendMessage(String destinationName, MessageContent message) {
        DestinationConfig destination = configurationService.get(destinationName);
        if (destination == null) {
            throw new IllegalArgumentException("The requested destination does not exist");
        }
        producer.send(destination, message);
    }

}
