package com.laboratorio.threadsapiinterface.exception;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 10/07/2024
 * @updated 02/05/2025
 */
public class ThreadsApiException extends RuntimeException {
    private Throwable causaOriginal = null;
    
    public ThreadsApiException(String message) {
        super(message);
    }
    
    public ThreadsApiException(String message, Throwable causaOriginal) {
        super(message, causaOriginal);
        this.causaOriginal = causaOriginal;
    }
    
    @Override
    public String getMessage() {
        if (this.causaOriginal != null) {
            return super.getMessage() + " | Causa original: " + this.causaOriginal.getMessage();
        }
        
        return super.getMessage();
    }
}