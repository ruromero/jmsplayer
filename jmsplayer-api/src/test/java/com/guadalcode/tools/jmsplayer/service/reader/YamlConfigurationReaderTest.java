package com.guadalcode.tools.jmsplayer.service.reader;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.Test;

import com.guadalcode.tools.jmsplayer.model.DestinationConfig;
import com.guadalcode.tools.jmsplayer.model.JMSProviderType;
import com.guadalcode.tools.jmsplayer.service.reader.impl.YamlConfigurationReader;

public class YamlConfigurationReaderTest {

    @Test
    public void testReadSimple() {
        ConfigurationReader reader = new YamlConfigurationReader();
        List<DestinationConfig> configs = reader.load("configuration/simple.yaml");
        
        assertThat(configs, notNullValue());
        assertThat(configs.size(), equalTo(1));
        
        DestinationConfig config = configs.get(0);
        assertThat(config.getName(), is("TestConfig"));
        assertThat(config.getEndpoint(), is("t3://localhost:7001"));
        assertThat(config.getConnectionFactory(), is("jms/defaultConnectionFactory"));
        assertThat(config.getDestinationName(), is("jms/myqueue"));
        assertThat(config.getProviderType(), is(JMSProviderType.WEBLOGIC));
        assertThat(config.getUsername(), is("weblogic"));
        assertThat(config.getPassword(), is("weblogic1"));
    }
    
    @Test
    public void testReadMultiple() {
        ConfigurationReader reader = new YamlConfigurationReader();
        List<DestinationConfig> configs = reader.load("configuration/multiple.yaml");
        
        assertThat(configs, notNullValue());
        assertThat(configs.size(), equalTo(2));
        
        DestinationConfig config = configs.get(0);
        assertThat(config.getName(), is("TestConfig"));
        assertThat(config.getEndpoint(), is("t3://localhost:7001"));
        assertThat(config.getConnectionFactory(), is("jms/defaultConnectionFactory"));
        assertThat(config.getDestinationName(), is("jms/myqueue"));
        assertThat(config.getProviderType(), is(JMSProviderType.WEBLOGIC));
        assertThat(config.getUsername(), is("weblogic"));
        assertThat(config.getPassword(), is("weblogic1"));
        
        config = configs.get(1);
        assertThat(config.getName(), is("TestConfig2"));
        assertThat(config.getEndpoint(), is("t3://localhost2:7001"));
        assertThat(config.getConnectionFactory(), is("jms/defaultConnectionFactory2"));
        assertThat(config.getDestinationName(), is("jms/myqueue2"));
        assertThat(config.getProviderType(), is(JMSProviderType.EMBEDDED_ACTIVEMQ));
        assertThat(config.getUsername(), is("weblogic2"));
        assertThat(config.getPassword(), is("weblogic12"));
    }
}
