package com.guadalcode.tools.jmsplayer.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.guadalcode.tools.jmsplayer.model.DestinationConfig;
import com.guadalcode.tools.jmsplayer.model.JMSProviderType;

/**
 * @author rromero
 *
 */
public class ConfigurationResourceTest extends AbstractResourceTest {

    private static final GenericType<List<DestinationConfig>> DEST_CONFIG_LIST_TYPE = new GenericType<List<DestinationConfig>>() {
    };
    
    @Override
    protected Class<?> getResourceClass() {
        return ConfigurationResource.class;
    }
    
    @Test
    public void testList() {
        List<DestinationConfig> configs = target("configuration").request(MediaType.APPLICATION_JSON).get(
                DEST_CONFIG_LIST_TYPE);
        assertTrue(configs.isEmpty());
    }

    @Test
    public void testCreateAndList() throws JsonParseException, JsonMappingException, IOException {
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

        List<DestinationConfig> configs = target("configuration").request(MediaType.APPLICATION_JSON).get(
                DEST_CONFIG_LIST_TYPE);
        assertFalse(configs.isEmpty());
        assertEquals(1, configs.size());
        DestinationConfig resultConfig = configs.get(0);
        assertEquals(config.getDestinationName(), resultConfig.getDestinationName());
    }
}
