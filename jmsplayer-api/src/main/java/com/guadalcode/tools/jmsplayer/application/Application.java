package com.guadalcode.tools.jmsplayer.application;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import com.guadalcode.tools.jmsplayer.service.configuration.ConfigurationService;

public class Application extends ResourceConfig {
    
    public Application() {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(ConfigurationService.class).to(ConfigurationService.class);
            }
        });
        ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();
    }
    
    
}
