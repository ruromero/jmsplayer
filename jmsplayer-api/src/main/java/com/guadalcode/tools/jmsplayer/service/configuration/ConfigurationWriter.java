package com.guadalcode.tools.jmsplayer.service.configuration;

import java.io.OutputStream;
import java.util.List;

import com.guadalcode.tools.jmsplayer.model.DestinationConfig;

public interface ConfigurationWriter {

    String toString(List<DestinationConfig> configs);
    
    OutputStream write(List<DestinationConfig> configs);
    
}
