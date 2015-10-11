package com.guadalcode.tools.jmsplayer.model;

public enum JMSProviderType {

    WEBLOGIC ("weblogic.jndi.WLInitialContextFactory"),
    EMBEDDED_ACTIVEMQ ("org.apache.activemq.jndi.ActiveMQInitialContextFactory");
    
    private String initialContextFactory;
    
    private JMSProviderType(String initialContextFactory) {
        this.initialContextFactory = initialContextFactory;
    }
    
    public String getInitialContextFactory() {
        return initialContextFactory;
    }

}
