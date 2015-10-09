package com.guadalcode.tools.jmsplayer.service.reader.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import com.guadalcode.tools.jmsplayer.model.DestinationConfig;
import com.guadalcode.tools.jmsplayer.model.JMSProviderType;
import com.guadalcode.tools.jmsplayer.service.reader.ConfigurationReader;

public class YamlConfigurationReader implements ConfigurationReader {

    private static final Logger logger = LogManager.getLogger(YamlConfigurationReader.class);

    @Override
    public List<DestinationConfig> load(String path) {
        List<DestinationConfig> configs = new ArrayList<>();
        Yaml yaml = new Yaml();
        List<?> objects = (List<?>) yaml.load(ClassLoader.getSystemResourceAsStream(path));
        for(Object object : objects) {
            DestinationConfig config = convert((Map<String, String>) object);
            logger.debug("Loaded config: {}", config.getName());
            configs.add(config);
        }
        return configs;
    }
    
    private DestinationConfig convert(Map<String, String> configMap) {
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
