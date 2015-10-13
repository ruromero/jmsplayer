package com.guadalcode.tools.jmsplayer.rest;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.guadalcode.tools.jmsplayer.model.DestinationConfig;
import com.guadalcode.tools.jmsplayer.service.configuration.ConfigurationService;

/**
 * @author rromero
 *
 */
@Path("configuration")
public class ConfigurationResource {

    private static final Logger logger = LogManager.getLogger(ConfigurationResource.class);

    private static final ConfigurationService CONFIG_SRV = ConfigurationService.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<DestinationConfig> list() {
        logger.debug("Requested all configurations");
        return CONFIG_SRV.getAll();
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public DestinationConfig get(@PathParam("name") String name) {
        logger.debug("Requested config {}", name);
        DestinationConfig config = CONFIG_SRV.get(name);
        if (config != null) {
            logger.debug("Found config with name {}", name);
            return config;
        } else {
            logger.info("Unable to find requested config: {}", name);
            throw new NotFoundException();
        }
    }

    @DELETE
    @Path("/{name}")
    public void delete(@PathParam("name") String name) {
        logger.debug("Removing destination {}", name);
        CONFIG_SRV.remove(name);
    }

    @POST
    @Path("/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("name") String name, DestinationConfig config) {
        logger.debug("Updating destination {}", name);
        CONFIG_SRV.update(name, config);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(DestinationConfig config) {
        logger.debug("Creating destination {}", config.getName());
        CONFIG_SRV.add(config);
    }
}
