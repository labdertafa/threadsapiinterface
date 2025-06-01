package com.laboratorio.api;

import com.laboratorio.clientapilibrary.exceptions.ApiClientException;
import com.laboratorio.clientapilibrary.utils.ReaderConfig;
import com.laboratorio.threadsapiinterface.ThreadsSessionApi;
import com.laboratorio.threadsapiinterface.exception.ThreadsApiException;
import com.laboratorio.threadsapiinterface.impl.ThreadsSessionApiImpl;
import com.laboratorio.threadsapiinterface.model.ThreadsSessionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 03/09/2024
 * @updated 01/05/2024
 */
public class ThreadsSessionApiTest {
    private ThreadsSessionApi sessionApi;
    
    @BeforeEach
    public void initTest() {
        ReaderConfig config = new ReaderConfig("config//threads_api.properties");
        String accessToken = config.getProperty("test_access_token");
        this.sessionApi = new ThreadsSessionApiImpl(accessToken);
    }
    
/*    @Test
    public void exchangeToken() {
        ThreadsSessionResponse response = this.sessionApi.exchangeToken();
        assertTrue(response.getExpires_in() > 0);
    } */
    
/*    @Test
    public void refreshSession() {
        ThreadsSessionResponse response = this.sessionApi.refreshSession();
        assertTrue(response.getExpires_in() > 0);
    } */
    
    @Test
    public void refreshSessionInvalidToken() {
        String token = "THQWJWR01Od0dmOHBVWktyaWVIZAF9RNWo0OWc4WFk2amE0SHhERlZA5c3FUdjZACUnZAIamZAtcU5ZAZAUt5a2p6MWt4MDFCNlVvWnpJYkpWb2RSankzMDJxTWpVSmtIcjlVbklIOFpMVU1pcVhzODdpMUFQZAXKHDKJHKADHAD";
        this.sessionApi = new ThreadsSessionApiImpl(token);
        
        assertThrows(ApiClientException.class, () -> this.sessionApi.refreshSession());
    }
}