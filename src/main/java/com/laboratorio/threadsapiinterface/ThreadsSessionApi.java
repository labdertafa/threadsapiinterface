package com.laboratorio.threadsapiinterface;

import com.laboratorio.threadsapiinterface.model.ThreadsSessionResponse;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 03/09/2024
 * @updated 03/09/2024
 */
public interface ThreadsSessionApi {
    // Cambia el access token por un token de larga duración
    ThreadsSessionResponse exchangeToken();
    
    // Obtiene un nuevo token de larga duración
    ThreadsSessionResponse refreshSession();
}