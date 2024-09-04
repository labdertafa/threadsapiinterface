package com.laboratorio.threadsapiinterface.utils;

import java.io.FileReader;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 16/08/2024
 * @updated 03/09/2024
 */
public class ThreadsApiConfig {
    private static final Logger log = LogManager.getLogger(ThreadsApiConfig.class);
    private static ThreadsApiConfig instance;
    private final Properties properties;

    private ThreadsApiConfig() {
        properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        try {
            this.properties.load(new FileReader("config//threads_api.properties"));
        } catch (Exception e) {
            log.error("Ha ocurrido un error leyendo el fichero de configuración del API de LinkedIn. Finaliza la aplicación!");
            log.error(String.format("Error: %s", e.getMessage()));
            if (e.getCause() != null) {
                log.error(String.format("Causa: %s", e.getCause().getMessage()));
            }
            System.exit(-1);
        }
    }

    public static ThreadsApiConfig getInstance() {
        if (instance == null) {
            synchronized (ThreadsApiConfig.class) {
                if (instance == null) {
                    instance = new ThreadsApiConfig();
                }
            }
        }
        
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}