package com.guadalcode.tools.jmsplayer.service.configuration;

import java.io.InputStream;
import java.util.List;

import com.guadalcode.tools.jmsplayer.model.DestinationConfig;

/**
 * @author rromero
 *
 */
public interface ConfigurationReader {
    
    List<DestinationConfig> loadFrom(String path);
    
    List<DestinationConfig> load(String content);
    
    List<DestinationConfig> load(InputStream content);
    
}
