package com.laboratorio.threadsapiinterface.imgur.util;

import com.laboratorio.clientapilibrary.utils.ReaderConfig;
import com.laboratorio.threadsapiinterface.imgur.ImgurImageApi;
import com.laboratorio.threadsapiinterface.imgur.exception.ImgurApiException;
import com.laboratorio.threadsapiinterface.imgur.model.ImgurTokenResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.2
 * @created 03/09/2024
 * @updated 30/12/2025
 */
public class TokenManager {
    protected static final Logger log = LogManager.getLogger(TokenManager.class);

    private TokenManager() {
    }
    
    public static boolean saveTokenInfo(ImgurTokenInfo info) throws Exception {
        ReaderConfig config = new ReaderConfig("config" + File.separator + "threads_api.properties");
        String file = config.getProperty("imgur_token_file");

        try (FileOutputStream fos = new FileOutputStream(file)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(info);

                return true;
            }
        }
    }
    
    public static ImgurTokenInfo loadTokenInfo() throws Exception {
        ReaderConfig config = new ReaderConfig("config" + File.separator + "threads_api.properties");
        String file = config.getProperty("imgur_token_file");
        
        try (FileInputStream fis = new FileInputStream(file)) {
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                return (ImgurTokenInfo)ois.readObject();
            }
        }
    }
    
    public static String getImgurAccessToken() {
        try {
            ImgurTokenInfo tokenInfo = loadTokenInfo();
            // Se verifica la validez del token
            if (tokenInfo.getExpirationDate().isAfter(LocalDateTime.now().plusSeconds(120))) {
                return tokenInfo.getAccessToken();
            }
            
            // Se pide un nuevo token
            ImgurImageApi imageApi = new ImgurImageApi();
            ImgurTokenResponse response = imageApi.refreshToken(tokenInfo.getRefreshToken());
            
            // Se almacena el nuevo token
            ImgurTokenInfo newTokenInfo = new ImgurTokenInfo(response);
            saveTokenInfo(newTokenInfo);
            
            return newTokenInfo.getAccessToken();
        } catch (Exception e) {
            throw new ImgurApiException(TokenManager.class.getName(), "Error obteniendo un access token válido de Imgur");
        }
    }
}