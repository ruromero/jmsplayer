package com.guadalcode.tools.jmsplayer.servlet;

import java.io.InputStream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.guadalcode.tools.jmsplayer.service.configuration.ConfigurationReader;
import com.guadalcode.tools.jmsplayer.service.configuration.ConfigurationService;

@WebListener
@Component
public class ConfigurationServlet implements ServletContextListener {

    private static final Logger logger = LogManager.getLogger(ConfigurationServlet.class);
    private static final String CONFIGURATION_PATH_PARAM = "jmsplayer.configuration.path";
    
    @Autowired
    private ConfigurationService configSrv;
    
    @Autowired
    private ConfigurationReader configReader;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        String path = event.getServletContext().getInitParameter(CONFIGURATION_PATH_PARAM);
        if (!Strings.isNullOrEmpty(path)) {
            logger.debug("Trying to configure destinations from: {}", path);
            InputStream inputStream = this.getClass().getResourceAsStream(path);
            if (inputStream != null) {
                logger.debug("Loaded file {} from classpath", path);
                configSrv.addAll(configReader.load(inputStream));
                logger.debug("Successfully imported configurations from file {}", path);
            } else {
                logger.warn("Unable to load file {}", path);
            }
        } else {
            logger.debug("No configuration file has been configured");
        }
    }

}
