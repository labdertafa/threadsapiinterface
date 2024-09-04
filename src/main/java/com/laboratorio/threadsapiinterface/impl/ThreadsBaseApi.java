package com.laboratorio.threadsapiinterface.impl;

import com.laboratorio.threadsapiinterface.utils.ThreadsApiConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 03/09/2024
 * @updated 03/09/2024
 */
public class ThreadsBaseApi {
    protected static final Logger log = LogManager.getLogger(ThreadsBaseApi.class);
    protected final String accessToken;
    protected final ThreadsApiConfig config;
    protected final String urlBase;

    public ThreadsBaseApi(String accessToken) {
        this.accessToken = accessToken;
        this.config = ThreadsApiConfig.getInstance();
        this.urlBase = this.config.getProperty("url_base_threads");
    }
    
    protected void logException(Exception e) {
        log.error("Error: " + e.getMessage());
        if (e.getCause() != null) {
            log.error("Causa: " + e.getMessage());
        }
    }
}