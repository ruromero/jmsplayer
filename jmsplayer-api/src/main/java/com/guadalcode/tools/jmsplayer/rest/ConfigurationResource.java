package com.guadalcode.tools.jmsplayer.rest;

import java.io.InputStream;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.guadalcode.tools.jmsplayer.model.DestinationConfig;
import com.guadalcode.tools.jmsplayer.service.configuration.ConfigurationReader;
import com.guadalcode.tools.jmsplayer.service.configuration.ConfigurationService;

/**
 * @author rromero
 *
 */
@Path("configuration")
@Component
public class ConfigurationResource {

    private static final Logger logger = LogManager.getLogger(ConfigurationResource.class);

    @Autowired
    private ServletContext servletContext;
    
    @Autowired
    private ConfigurationService configurationService;
    
    @Value("#{appProperties['destinations.configuration']}")
    private String path;
    
    @Autowired
    private ConfigurationReader configReader;
    
    @PostConstruct
    public void configure() {
        if (!Strings.isNullOrEmpty(path)) {
            logger.debug("Trying to configure destinations from: {}", path);
            InputStream inputStream = servletContext.getResourceAsStream(path);
            if (inputStream != null) {
                logger.debug("Loaded file {} from classpath", path);
                configurationService.addAll(configReader.load(inputStream));
                logger.debug("Successfully imported configurations from file {}", path);
            } else {
                logger.warn("Unable to load file {}", path);
            }
        } else {
            logger.debug("No configuration file has been configured");
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<DestinationConfig> list() {
        logger.debug("Requested all configurations");
        return configurationService.getAll();
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public DestinationConfig get(@PathParam("name") String name) {
        logger.debug("Requested config {}", name);
        DestinationConfig config = configurationService.get(name);
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
        configurationService.remove(name);
    }

    @POST
    @Path("/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("name") String name, DestinationConfig config) {
        logger.debug("Updating destination {}", name);
        configurationService.update(name, config);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(DestinationConfig config) {
        logger.debug("Creating destination {}", config.getName());
        configurationService.add(config);
    }
}
