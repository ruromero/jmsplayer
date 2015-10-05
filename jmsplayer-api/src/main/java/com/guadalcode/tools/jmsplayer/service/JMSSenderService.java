package com.guadalcode.tools.jmsplayer.service;

import com.guadalcode.tools.jmsplayer.model.DestinationConfig;
import com.guadalcode.tools.jmsplayer.model.MessageContent;

public interface JMSSenderService {

    void send(DestinationConfig destination, MessageContent message);
    
}
