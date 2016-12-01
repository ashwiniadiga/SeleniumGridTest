package com.test.conf;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;


public class SeleniumConfig {
    private static final Logger LOG = LoggerFactory.getLogger(SeleniumConfig.class);

    public static Config CONFIG = createConfigObject("selenium.conf");

// This will load all configuration from Resource Directory and adds to Path
    public static Config createConfigObject(String configPath){

        ClassLoader classLoader = SeleniumConfig.class.getClassLoader();
        URL classFileUrl = classLoader.getResource(configPath);
        File classFile = null;
        if (classFileUrl != null) {
            try {
                classFile = new File(classFileUrl.getFile());
            } catch (Exception e) {
                LOG.error("Error while loading the File");
            }
        }
        CONFIG = ConfigFactory.defaultOverrides()
                .withFallback(ConfigFactory.parseFile(classFile))
                .withFallback(ConfigFactory.load());

        return CONFIG;
    }

}
