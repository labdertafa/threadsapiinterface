package com.laboratorio.threadsapiinterface.imgur.util;

import com.laboratorio.threadsapiinterface.imgur.ImgurImageApi;
import com.laboratorio.threadsapiinterface.imgur.exception.ImgurApiException;
import com.laboratorio.threadsapiinterface.imgur.model.ImgurTokenResponse;
import com.laboratorio.threadsapiinterface.utils.ThreadsApiConfig;
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
 * @version 1.0
 * @created 03/09/2024
 * @updated 04/09/2024
 */
public class TokenManager {
    protected static final Logger log = LogManager.getLogger(TokenManager.class);
    
    public static boolean saveTokenInfo(ImgurTokenInfo info) throws Exception {
        String file = ThreadsApiConfig.getInstance().getProperty("imgur_token_file");

        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(info);
        
        return true;
    }
    
    public static ImgurTokenInfo loadTokenInfo() throws Exception {
        String file = ThreadsApiConfig.getInstance().getProperty("imgur_token_file");
        
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);

        return (ImgurTokenInfo)ois.readObject();
    }
    
    public static String getImgurAccessToken() {
        try {
            ImgurTokenInfo tokenInfo = loadTokenInfo();
            // Se verifica la validez del token
            if (tokenInfo.getExpirationDate().isAfter(LocalDateTime.now().plusSeconds(120))) {
                return tokenInfo.getAccess_token();
            }
            
            // Se pide un nuevo token
            ImgurImageApi imageApi = new ImgurImageApi();
            ImgurTokenResponse response = imageApi.refreshToken(tokenInfo.getAccess_token(), tokenInfo.getRefresh_token());
            
            // Se almacena el nuevo token
            ImgurTokenInfo newTokenInfo = new ImgurTokenInfo(response);
            saveTokenInfo(newTokenInfo);
            
            return newTokenInfo.getAccess_token();
        } catch (Exception e) {
            throw new ImgurApiException(TokenManager.class.getName(), "Error obteniendo un access token válido de Imgur");
        }
    }
}