package com.guadalcode.tools.service.jms;

import com.guadalcode.tools.model.jms.DestinationConfig;
import com.guadalcode.tools.model.jms.MessageContent;

public interface JMSSenderService {

    void send(DestinationConfig destination, MessageContent message);
    
}
