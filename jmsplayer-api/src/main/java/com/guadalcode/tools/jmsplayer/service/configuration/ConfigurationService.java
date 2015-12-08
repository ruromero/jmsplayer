package com.guadalcode.tools.jmsplayer.service.configuration;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.guadalcode.tools.jmsplayer.model.DestinationConfig;

/**
 * @author rromero
 *
 */
@Service
public class ConfigurationService {

    private static final Logger logger = LogManager.getLogger(ConfigurationService.class);

    private Map<String, DestinationConfig> destinations = Collections.synchronizedMap(new HashMap<String, DestinationConfig>());
    
    public Collection<DestinationConfig> getAll() {
        return destinations.values();
    }
    
    public DestinationConfig add(DestinationConfig destination) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination must not be null");
        }
        if (Strings.isNullOrEmpty(destination.getName())) {
            throw new IllegalArgumentException("Destination name must not be empty");
        }
        destinations.put(destination.getName(), destination);
        logger.debug("Added destination {}", destination.getName());
        return destination;
    }
    
    public void addAll(List<DestinationConfig> configs) {
        if (configs == null || configs.isEmpty()) {
            logger.warn("Trying to add an empty set of configurations");
            return;
        }
        for (DestinationConfig config : configs) {
            add(config);
        }
    }

    public DestinationConfig remove(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Destination name must not be null");
        }
        DestinationConfig destination = destinations.remove(name);
        logger.debug("Removed configuration {}", name);
        return destination;
    }

    public DestinationConfig get(String name) {
        if (Strings.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("The name must not be null or empty");
        }
        logger.debug("Retrieving configuration {}", name);
        return destinations.get(name);
    }

    public void update(String name, DestinationConfig config) {
        if (Strings.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("The name must not be null or empty");
        }
        if (destinations.containsKey(name)) {
            destinations.put(name, config);
            logger.debug("Updated configuration {}", name);
        }
    }
}
