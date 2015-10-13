package com.guadalcode.tools.jmsplayer.util.server;

import org.apache.activemq.broker.BrokerService;

/**
 * Embedded ActiveMQ JMS {@link BrokerService} which can be used for testing.
 *
 * @author rromero
 *
 */
public class ActiveMQServer {

    private BrokerService service = new BrokerService();

    /**
     * Will start non persistent {@link BrokerService} with a default connector
     * at tcp://localhost:61616
     */
    public void start() throws Exception {
        service.setPersistent(false);
        service.addConnector("tcp://localhost:61616");
        service.start();
    }

    /**
     * Stops the {@link BrokerService}
     * 
     * @throws Exception
     */
    public void stop() throws Exception {
        service.stop();
    }

    public BrokerService getService() {
        return service;
    }
}
