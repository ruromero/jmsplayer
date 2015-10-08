package com.guadalcode.tools.jmsplayer.model;

public class DestinationConfig {

    private String name;
    private String endpoint;
    private String connectionFactory;
    private String destinationName;
    private String username;
    private String password;
    private JMSProviderType providerType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(String connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JMSProviderType getProviderType() {
        return providerType;
    }

    public void setProviderType(JMSProviderType providerType) {
        this.providerType = providerType;
    }

}
