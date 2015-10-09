package com.guadalcode.tools.jmsplayer.service.reader;

import java.util.List;

import com.guadalcode.tools.jmsplayer.model.DestinationConfig;

public interface ConfigurationReader {
    
    List<DestinationConfig> load(String path);
    
}
