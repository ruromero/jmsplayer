package com.guadalcode.tools.jmsplayer.service.configuration.impl;

import java.io.OutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.guadalcode.tools.jmsplayer.model.DestinationConfig;
import com.guadalcode.tools.jmsplayer.service.configuration.ConfigurationWriter;

@Service
public class YamlConfigurationWriter implements ConfigurationWriter {

    @Override
    public String toString(List<DestinationConfig> configs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutputStream write(List<DestinationConfig> configs) {
        // TODO Auto-generated method stub
        return null;
    }

}
