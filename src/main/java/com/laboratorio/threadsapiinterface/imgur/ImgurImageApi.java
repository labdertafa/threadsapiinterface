package com.laboratorio.threadsapiinterface.imgur;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.laboratorio.clientapilibrary.ApiClient;
import com.laboratorio.clientapilibrary.exceptions.ApiClientException;
import com.laboratorio.clientapilibrary.model.ApiMethodType;
import com.laboratorio.clientapilibrary.model.ApiRequest;
import com.laboratorio.clientapilibrary.model.ApiResponse;
import com.laboratorio.clientapilibrary.utils.ReaderConfig;
import com.laboratorio.threadsapiinterface.imgur.model.ImgurImageUpload;
import com.laboratorio.threadsapiinterface.imgur.model.ImgurTokenResponse;
import com.laboratorio.threadsapiinterface.imgur.util.TokenManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 03/09/2024
 * @updated 04/05/2025
 */
public class ImgurImageApi {
    protected static final Logger log = LogManager.getLogger(ImgurImageApi.class);
    protected final ApiClient client;
    private final ReaderConfig config;
    private final String urlBase;
    protected final Gson gson;

    public ImgurImageApi() {
        this.client = new ApiClient();
        this.config = new ReaderConfig("config//threads_api.properties");
        this.urlBase = this.config.getProperty("url_base_imgur");
        this.gson = new Gson();
    }
    
    protected void logException(Exception e) {
        log.error("Error: " + e.getMessage());
        if (e.getCause() != null) {
            log.error("Causa: " + e.getCause().getMessage());
        }
    }
    
    public ImgurTokenResponse refreshToken(String accessToken, String refreshToken) {
        String endpoint = this.config.getProperty("imgur_token_endpoint");
        int okStatus = Integer.parseInt(this.config.getProperty("imgur_token_valor_ok"));
        String clientId = this.config.getProperty("imgur_app_client_id");
        String clientSecret = this.config.getProperty("imgur_app_client_secret");
        
        try {
            String url = endpoint;
            ApiRequest request = new ApiRequest(url, okStatus, ApiMethodType.POST);
            request.addTextFormData("refresh_token", refreshToken);
            request.addTextFormData("client_id", clientId);
            request.addTextFormData("client_secret", clientSecret);
            request.addTextFormData("grant_type", "refresh_token");
            
            ApiResponse response = this.client.executeApiRequest(request);

            return this.gson.fromJson(response.getResponseStr(), ImgurTokenResponse.class);
        } catch (JsonSyntaxException e) {
            logException(e);
            throw  e;
        } catch (ApiClientException e) {
            throw  e;
        }
    }
    
    public ImgurImageUpload uploadImage(String imagePath, String title, String description) throws Exception {
        String endpoint = this.config.getProperty("imageUpload_endpoint");
        int okStatus = Integer.parseInt(this.config.getProperty("imageUpload_valor_ok"));
        
        try {
            String accessToken = TokenManager.getImgurAccessToken();
            log.info("Access token: " + accessToken);
            
            String url = this.urlBase + "/" + endpoint;
            log.info("URL: " + url);
            ApiRequest request = new ApiRequest(url, okStatus, ApiMethodType.POST);
            request.addFileFormData("image", imagePath);
            request.addTextFormData("type", "image");
            request.addTextFormData("title", title);
            request.addTextFormData("description", description);
            request.addApiHeader("Authorization", "Bearer " + accessToken);
            
            ApiResponse response = this.client.executeApiRequest(request);
            
            return this.gson.fromJson(response.getResponseStr(), ImgurImageUpload.class);
        } catch (JsonSyntaxException e) {
            logException(e);
            throw  e;
        } catch (ApiClientException e) {
            throw  e;
        }
    }
}