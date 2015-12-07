package com.guadalcode.tools.jmsplayer.rest;

import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import com.guadalcode.tools.jmsplayer.model.MessageContent;

/**
 * @author rromero
 *
 */
public class MessageResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        set(TestProperties.LOG_TRAFFIC, true);
        set(TestProperties.DUMP_ENTITY, true);
        return new ResourceConfig(ConfigurationResource.class);
    }

    @Test
    public void testSend() {
        MessageContent msgReq = new MessageContent("message content", "message type");
        Entity<MessageContent> msgReqEntity = Entity.json(msgReq);
        MessageContent msg = target("messages/testDestination").request().put(msgReqEntity, MessageContent.class);
        assertNotNull(msg);
        assertNotNull(msg.getId());
    }

}
