package com.laboratorio.api;

import com.laboratorio.threadsapiinterface.ThreadsStatusApi;
import com.laboratorio.threadsapiinterface.impl.ThreadsStatusApiImpl;
import com.laboratorio.threadsapiinterface.model.ThreadsPost;
import com.laboratorio.threadsapiinterface.model.ThreadsPostResponse;
import com.laboratorio.threadsapiinterface.utils.ThreadsApiConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 03/09/2024
 * @updated 04/09/2024
 */
public class ThreadsStatusApiTest {
    private ThreadsStatusApi statusApi;
    
    @BeforeEach
    public void initTest() {
        String accessToken = ThreadsApiConfig.getInstance().getProperty("test_access_token");
        this.statusApi = new ThreadsStatusApiImpl(accessToken);
    }
    
    @Test
    public void retrievePost() {
        String id = "17917637687877200";
        
        ThreadsPost post = this.statusApi.retrievePost(id);
        
        assertEquals(id, post.getId());
    }
    
    @Test
    public void sendTextStatus() {
        String text = "Este es un contenido de prueba desde Postman";
        
        ThreadsPostResponse response = this.statusApi.postStatus(text);
        
        assertTrue(!response.getId().isEmpty());
        
        response = this.statusApi.publishStatus(response.getId());
        
        assertTrue(!response.getId().isEmpty());
    }
    
    @Test
    public void sendImageStatus() {
        String text = "Este es un contenido de prueba desde Postman";
        String imageUrl = "https://www.cyberclick.es/hubfs/blog/Relaciones%20publicas%20que%20son%20y%20que%20ventajas%20aportan.jpg";
        
        ThreadsPostResponse response = this.statusApi.postStatus(text, imageUrl);
        
        assertTrue(!response.getId().isEmpty());
        
        response = this.statusApi.publishStatus(response.getId());
        
        assertTrue(!response.getId().isEmpty());
    }
    
    @Test
    public void postStatusWithImagePath() {
        String text = "Este es un contenido de prueba desde Postman";
        String imagePath = "C:\\Users\\rafa\\Pictures\\Formula_1\\Spa_1950.jpg";
        
        ThreadsPostResponse response = this.statusApi.postStatus(text, imagePath);
        
        assertTrue(!response.getId().isEmpty());
        
        response = this.statusApi.publishStatus(response.getId());
        
        assertTrue(!response.getId().isEmpty());
    }
}