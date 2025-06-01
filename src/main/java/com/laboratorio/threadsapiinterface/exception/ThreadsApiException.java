package com.laboratorio.threadsapiinterface.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 10/07/2024
 * @updated 01/05/2025
 */
public class ThreadsApiException extends RuntimeException {
    private static final Logger log = LogManager.getLogger(ThreadsApiException.class);
    
    public ThreadsApiException(String className, String message, Exception e) {
        super(message);
        this.logException(className, message, e);
    }
    
    private void logException(String className, String message, Exception e) {
        log.error("Error en clase {}: {}", className, message);
        log.error("Error: {}", e.getMessage());
        if (e.getCause() != null) {
            log.error("Causa: {}", e.getCause().getMessage());
        }
    }
}