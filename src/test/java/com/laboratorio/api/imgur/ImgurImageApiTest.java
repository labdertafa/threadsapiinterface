package com.laboratorio.api.imgur;

import com.laboratorio.threadsapiinterface.imgur.ImgurImageApi;
import com.laboratorio.threadsapiinterface.imgur.model.ImgurImageUpload;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 04/09/2024
 * @updated 04/09/2024
 */
public class ImgurImageApiTest {
    @Test
    public void uploadImage() throws Exception {
        String imagePath = "C:\\Users\\rafa\\Pictures\\Formula_1\\Spa_1950.jpg";
        String title = "Título de prueba";
        String description = "Descripción de prueba";
        
        ImgurImageApi imageApi = new ImgurImageApi();
        ImgurImageUpload upload = imageApi.uploadImage(imagePath, title, description);
        
        assertTrue(upload.isSuccess());
        assertEquals(title, upload.getData().getTitle());
        assertEquals(description, upload.getData().getDescription());
    }
}