package com.guadalcode.tools.jmsplayer.util.server;

import org.apache.activemq.broker.BrokerService;

public class ActiveMQServer {

    private BrokerService brokerService = new BrokerService();
    
    public void start() throws Exception {
	brokerService.setPersistent(false);
	brokerService.start();
    }
    
    public void stop() throws Exception {
	brokerService.stop();
    }
}
