package com.guadalcode.tools.jmsplayer.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.ws.rs.client.Entity;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;

import com.guadalcode.tools.jmsplayer.model.MessageContent;
import com.guadalcode.tools.jmsplayer.service.JMSService;

/**
 * @author rromero
 *
 */
public class MessageResourceTest extends JerseySpringTest {

    @Override
    protected Class<?> getResourceClass() {
        return MessageResource.class;
    }

    @Autowired
    private JMSService jmsService;

    @Test
    public void testSend() {
        MessageContent msgReq = new MessageContent("message content", "message type");
        Entity<MessageContent> msgReqEntity = Entity.json(msgReq);
        String destination = "testDestination";

        String msgId = target("messages/" + destination).request().post(msgReqEntity, String.class);

        ArgumentCaptor<MessageContent> msgCaptor = ArgumentCaptor.forClass(MessageContent.class);
        verify(jmsService, times(1)).sendMessage(eq(destination), msgCaptor.capture());
        assertNotNull(msgId);
        assertEquals(msgCaptor.getValue().getId().toString(), msgId);
        assertEquals(msgReq.getText(), msgCaptor.getValue().getText());
        assertEquals(msgReq.getType(), msgCaptor.getValue().getType());
    }

}
