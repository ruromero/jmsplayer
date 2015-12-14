package com.guadalcode.tools.jmsplayer.rest;

import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Entity;

import org.junit.Test;

import com.guadalcode.tools.jmsplayer.model.MessageContent;

/**
 * @author rromero
 *
 */
public class MessageResourceTest extends AbstractResourceTest {

    @Override
    protected Class<?> getResourceClass() {
        return MessageResource.class;
    }

    @Test
    public void testSend() {
        MessageContent msgReq = new MessageContent("message content", "message type");
        Entity<MessageContent> msgReqEntity = Entity.json(msgReq);
        String msgId = target("messages/testDestination").request().post(msgReqEntity, String.class);
        assertNotNull(msgId);
    }

}
