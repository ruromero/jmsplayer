package com.guadalcode.tools.jmsplayer.service.configuration.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import com.guadalcode.tools.jmsplayer.model.DestinationConfig;
import com.guadalcode.tools.jmsplayer.model.JMSProviderType;
import com.guadalcode.tools.jmsplayer.service.configuration.ConfigurationReader;

/**
 * @author rromero
 *
 */
public class YamlConfigurationReader implements ConfigurationReader {

    private static final Logger logger = LogManager.getLogger(YamlConfigurationReader.class);

    @Override
    public List<DestinationConfig> loadFrom(String path) {
        List<?> objects = (List<?>) new Yaml().load(ClassLoader.getSystemResourceAsStream(path));
        return load(objects);
    }
    
    @Override
    public List<DestinationConfig> load(String content) {
        List<?> objects = (List<?>) new Yaml().load(content);
        return load(objects);
    }
    
    @Override
    public List<DestinationConfig> load(InputStream content) {
        List<?> objects = (List<?>) new Yaml().load(content);
        return load(objects);
    }
    
    private List<DestinationConfig> load(List<?> objects) {
        List<DestinationConfig> configs = new ArrayList<>();
        for(Object object : objects) {
            DestinationConfig config = convert(object);
            logger.debug("Loaded config: {}", config.getName());
            configs.add(config);
        }
        return configs;
    }
    
    @SuppressWarnings("unchecked")
    private DestinationConfig convert(Object configObject) {
        if(!(configObject instanceof Map)) {
            throw new IllegalArgumentException("Error parsing the configuration object. It is not a java.util.Map");
        }
        Map<String, String> configMap = (Map<String, String>) configObject;
        DestinationConfig config = new DestinationConfig();
        config.setName(configMap.get("name"));
        config.setEndpoint(configMap.get("endpoint"));
        config.setConnectionFactory(configMap.get("connectionFactory"));
        config.setDestinationName(configMap.get("destinationName"));
        config.setUsername(configMap.get("username"));
        config.setPassword(configMap.get("password"));
        config.setProviderType(JMSProviderType.valueOf(configMap.get("providerType")));
        return config;
    }

}
