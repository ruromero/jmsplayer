package com.guadalcode.tools.jmsplayer.model;

/**
 * Enum containing all the supported connections. Any type listed here should exist
 * in the classpath
 * 
 * @author rromero
 *
 */
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
