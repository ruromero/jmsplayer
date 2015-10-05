package com.guadalcode.tools.jmsplayer.util.server;

import org.apache.activemq.broker.BrokerService;

public class ActiveMQServer {

    private BrokerService service = new BrokerService();
    
    public void start() throws Exception {
	service.setPersistent(false);
	service.addConnector("tcp://localhost:61616");
	service.start();
    }
    
    public void stop() throws Exception {
	service.stop();
    }
    
    public BrokerService getService() {
	return service;
    }
}
