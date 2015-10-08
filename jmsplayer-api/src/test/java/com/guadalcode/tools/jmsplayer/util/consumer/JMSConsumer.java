package com.guadalcode.tools.jmsplayer.util.consumer;

import java.util.List;

import com.guadalcode.tools.jmsplayer.model.MessageContent;

public interface JMSConsumer {

    void start();

    void stop() throws Exception;

    List<MessageContent> getMessages();

    void clear();

    int size();

    void assertReceived(MessageContent message);

    void assertNotReceived(MessageContent message);

}
