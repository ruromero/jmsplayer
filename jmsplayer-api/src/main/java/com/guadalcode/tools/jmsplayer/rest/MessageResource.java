package com.guadalcode.tools.jmsplayer.rest;

import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.guadalcode.tools.jmsplayer.model.MessageContent;
import com.guadalcode.tools.jmsplayer.service.JMSService;

/**
 * @author rromero
 *
 */
@Path("messages")
public class MessageResource {

    private static final Logger logger = LogManager.getLogger(MessageResource.class);

    @Inject
    private JMSService jmsService;

    @POST
    @Path("/{destination}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public UUID send(@PathParam("destination") String destination, @FormParam("message") MessageContent message) {
        message.setId(UUID.randomUUID());
        logger.debug("Try to send message to {} with ID: ", destination, message.getId());
        jmsService.sendMessage(destination, message);
        return message.getId();
    }

}
