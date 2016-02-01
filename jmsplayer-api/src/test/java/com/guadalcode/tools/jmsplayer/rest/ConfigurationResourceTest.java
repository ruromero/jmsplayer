package com.guadalcode.tools.jmsplayer.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.guadalcode.tools.jmsplayer.model.DestinationConfig;
import com.guadalcode.tools.jmsplayer.model.JMSProviderType;
import com.guadalcode.tools.jmsplayer.service.configuration.ConfigurationReader;
import com.guadalcode.tools.jmsplayer.service.configuration.ConfigurationService;

/**
 * @author rromero
 *
 */
public class ConfigurationResourceTest extends JerseySpringTest {

    private static final GenericType<List<DestinationConfig>> DEST_CONFIG_LIST_TYPE = new GenericType<List<DestinationConfig>>() {
    };

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private ConfigurationService configurationService;

    @Value("#{appProperties['destinations.configuration']}")
    private String path;

    @Autowired
    private ConfigurationReader configReader;

    @Override
    protected Class<?> getResourceClass() {
        return ConfigurationResource.class;
    }

    @Test
    public void testCreate() throws JsonParseException, JsonMappingException, IOException {
        DestinationConfig config = new DestinationConfig();
        config.setName("test");
        config.setConnectionFactory("ConnFactory");
        config.setEndpoint("Endpoint");
        config.setUsername("Username");
        config.setPassword("Password");
        config.setDestinationName("Destination name");
        config.setProviderType(JMSProviderType.WEBLOGIC);
        Entity<DestinationConfig> configEntity = Entity.json(config);

        target("configuration").request().put(configEntity, DestinationConfig.class);

        verify(configurationService, times(1)).add(config);
    }

    @Test
    public void testList() throws JsonParseException, JsonMappingException, IOException {
    	DestinationConfig config = new DestinationConfig();
    	config.setName("test");
    	config.setConnectionFactory("ConnFactory");
    	config.setEndpoint("Endpoint");
    	config.setUsername("Username");
    	config.setPassword("Password");
    	config.setDestinationName("Destination name");
    	config.setProviderType(JMSProviderType.WEBLOGIC);
    	List<DestinationConfig> expectedConfigs = Arrays.asList(config);
    	when(configurationService.getAll()).thenReturn(expectedConfigs);

    	List<DestinationConfig> configs = target("configuration").request(MediaType.APPLICATION_JSON).get(
    			DEST_CONFIG_LIST_TYPE);

    	assertFalse(configs.isEmpty());
    	assertEquals(expectedConfigs.size(), configs.size());
    	assertEquals(expectedConfigs.get(0), configs.get(0));
    	verify(configurationService, times(1)).getAll();
    }
}
