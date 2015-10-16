package com.guadalcode.tools.jmsplayer.application;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import com.guadalcode.tools.jmsplayer.service.JMSProducer;
import com.guadalcode.tools.jmsplayer.service.JMSProducerImpl;
import com.guadalcode.tools.jmsplayer.service.JMSService;
import com.guadalcode.tools.jmsplayer.service.configuration.ConfigurationReader;
import com.guadalcode.tools.jmsplayer.service.configuration.ConfigurationService;
import com.guadalcode.tools.jmsplayer.service.configuration.impl.YamlConfigurationReader;

public class Application extends ResourceConfig {
    
    private static final Logger logger = LogManager.getLogger(Application.class);
    
    @Inject
    public Application(final ServletContext servletContext) {
        logger.debug("Registering application configuration");
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(ConfigurationService.class).to(ConfigurationService.class).in(Singleton.class);
                bind(YamlConfigurationReader.class).to(ConfigurationReader.class).in(Singleton.class);
                bind(JMSService.class).to(JMSService.class).in(Singleton.class);
                bind(JMSProducerImpl.class).to(JMSProducer.class).in(Singleton.class);
                logger.debug("Resources configured");
            }
        });
        packages(true, "com.guadalcode.tools.jmsplayer");
    }
}
