package com.guadalcode.tools.jmsplayer.rest;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guadalcode.tools.jmsplayer.model.MessageContent;
import com.guadalcode.tools.jmsplayer.service.JMSService;

/**
 * @author rromero
 *
 */
@Path("messages")
@Component
public class MessageResource {

    private static final Logger logger = LogManager.getLogger(MessageResource.class);

    @Autowired
    private JMSService jmsService;

    @POST
    @Path("/{destination}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String send(@PathParam("destination") String destination, MessageContent message) {
        message.setId(UUID.randomUUID());
        logger.debug("Try to send message to {} with ID: ", destination, message.getId());
        jmsService.sendMessage(destination, message);
        logger.debug("Message sent to {} with ID: ", destination, message.getId());
        return message.getId().toString();
    }

}
