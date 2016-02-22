package com.guadalcode.tools.jmsplayer.model;

/**
 * @author rromero
 *
 */
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

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((connectionFactory == null) ? 0 : connectionFactory
						.hashCode());
		result = prime * result
				+ ((destinationName == null) ? 0 : destinationName.hashCode());
		result = prime * result
				+ ((endpoint == null) ? 0 : endpoint.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((providerType == null) ? 0 : providerType.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DestinationConfig other = (DestinationConfig) obj;
		if (connectionFactory == null) {
			if (other.connectionFactory != null)
				return false;
		} else if (!connectionFactory.equals(other.connectionFactory))
			return false;
		if (destinationName == null) {
			if (other.destinationName != null)
				return false;
		} else if (!destinationName.equals(other.destinationName))
			return false;
		if (endpoint == null) {
			if (other.endpoint != null)
				return false;
		} else if (!endpoint.equals(other.endpoint))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (providerType != other.providerType)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
