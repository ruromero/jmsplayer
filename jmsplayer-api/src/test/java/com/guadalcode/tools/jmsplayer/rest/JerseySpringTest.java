package com.guadalcode.tools.jmsplayer.rest;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Abstract class to be extended by any Test which pretends to test a REST Resource with Spring dependencies.
 * If some class requires a different Spring configuration just needs to declare itself its own {@link ContextConfiguration} annotation.
 * <p><b>Note</b> that this class, with a proper Spring configuration file can be also used for <b>integration testing</b>
 * @author romerru
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-application-context.xml")
public abstract class JerseySpringTest {

    private JerseyTest jerseyTest;

    public final WebTarget target(String path) {
        return jerseyTest.target(path);
    }

    @Before
    public void setup() throws Exception {
        jerseyTest.setUp();
    }

    @After
    public void tearDown() throws Exception {
        jerseyTest.tearDown();
    }

    @Autowired
    public void setApplicationContext(final ApplicationContext context)
    {
        jerseyTest = new JerseyTest()
        {
            @Override
            protected Application configure()
            {
                set(TestProperties.LOG_TRAFFIC, true);
                set(TestProperties.DUMP_ENTITY, true);

                ResourceConfig application = JerseySpringTest.this.configure();
                application.property("contextConfig", context);
                return application;
            }
        };
    }

    /**
     * The class of the REST Resource under test
     */
    protected abstract Class<?> getResourceClass();

    private ResourceConfig configure() {
        return new ResourceConfig(getResourceClass());
    }

}
