package com.guadalcode.tools.jmsplayer.util.consumer;

import com.guadalcode.tools.jmsplayer.model.DestinationConfig;
import com.guadalcode.tools.jmsplayer.util.consumer.impl.EmbeddedActiveMQJMSConsumer;
import com.guadalcode.tools.jmsplayer.util.consumer.impl.WeblogicJMSConsumer;

public class JMSConsumerFactory {

    public static JMSConsumer build(DestinationConfig config) {
	if (config == null || config.getProviderType() == null) {
	    throw new IllegalArgumentException("Neither the JMS Consumer nor the JMS Consumer Type should be null");
	}
	switch (config.getProviderType()) {
	case WEBLOGIC:
	    return new WeblogicJMSConsumer(config);
	case EMBEDDED_ACTIVEMQ:
	    return new EmbeddedActiveMQJMSConsumer(config);
	default:
	    throw new IllegalArgumentException("Unable to find a suitable Consumer instance");
	}
    }
}
