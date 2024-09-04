package com.laboratorio.utils;

import com.laboratorio.threadsapiinterface.imgur.util.TokenManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 03/09/2024
 * @updated 04/09/2024
 */
public class TokenManagerTest {
    @Test
    public void getImgurAccessToken() throws Exception {
        String token = TokenManager.getImgurAccessToken();
        
        assertTrue(!token.isEmpty());
    }
}