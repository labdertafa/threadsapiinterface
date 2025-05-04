package com.laboratorio.api;

import com.laboratorio.clientapilibrary.utils.ReaderConfig;
import com.laboratorio.threadsapiinterface.ThreadsStatusApi;
import com.laboratorio.threadsapiinterface.impl.ThreadsStatusApiImpl;
import com.laboratorio.threadsapiinterface.model.ThreadsStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 03/09/2024
 * @updated 04/05/2025
 */
public class ThreadsStatusApiTest {
    private ThreadsStatusApi statusApi;
    
    @BeforeEach
    public void initTest() {
        ReaderConfig config = new ReaderConfig("config//threads_api.properties");
        String accessToken = config.getProperty("test_access_token");
        String threads_user_id = "26835929882687714";
        this.statusApi = new ThreadsStatusApiImpl(accessToken, threads_user_id);
    }
    
    @Test
    public void retrievePost() {
        String id = "17917637687877200";
        
        ThreadsStatus post = this.statusApi.retrievePost(id);
        
        assertEquals(id, post.getId());
    }
    
    @Test
    public void sendTextStatus() {
        String text = "Este es un contenido de prueba desde Postman";
        
        ThreadsStatus status  = this.statusApi.postStatus(text);
        
        assertTrue(!status.getId().isEmpty());
    }
    
    @Test
    public void postStatusWithImagePath() {
        String text = "Este es un contenido de prueba desde Postman";
        String imagePath = "C:\\Users\\rafa\\Pictures\\Formula_1\\Spa_1950.jpg";
        
        ThreadsStatus status  = this.statusApi.postStatus(text, imagePath);
        
        assertTrue(!status.getId().isEmpty());
    }
}