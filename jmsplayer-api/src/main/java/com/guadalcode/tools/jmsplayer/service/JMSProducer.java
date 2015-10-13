package com.guadalcode.tools.jmsplayer.service;

import com.guadalcode.tools.jmsplayer.model.DestinationConfig;
import com.guadalcode.tools.jmsplayer.model.MessageContent;

/**
 * @author rromero
 *
 */
public interface JMSProducer {

    void send(DestinationConfig destination, MessageContent message);

}
