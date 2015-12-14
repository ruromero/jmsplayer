package com.guadalcode.tools.jmsplayer.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.springframework.web.context.ContextLoaderListener;

public abstract class AbstractResourceTest extends JerseyTest {


    protected abstract Class<?> getResourceClass();
    
    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }

    @Override
    protected DeploymentContext configureDeployment() {
        set(TestProperties.LOG_TRAFFIC, true);
        set(TestProperties.DUMP_ENTITY, true);
        return ServletDeploymentContext
                .forServlet(new ServletContainer(new ResourceConfig(getResourceClass())))
                .addListener(ContextLoaderListener.class)
                .contextParam("contextConfigLocation", "classpath:testApplicationContext.xml")
                .build();
    }
    
}
