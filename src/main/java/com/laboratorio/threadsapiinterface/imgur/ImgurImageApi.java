package com.laboratorio.threadsapiinterface.imgur;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.laboratorio.threadsapiinterface.imgur.exception.ImgurApiException;
import com.laboratorio.threadsapiinterface.imgur.model.ImgurImageUpload;
import com.laboratorio.threadsapiinterface.imgur.model.ImgurTokenResponse;
import com.laboratorio.threadsapiinterface.imgur.util.TokenManager;
import com.laboratorio.threadsapiinterface.utils.ThreadsApiConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataWriter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 03/09/2024
 * @updated 04/09/2024
 */
public class ImgurImageApi {
    protected static final Logger log = LogManager.getLogger(ImgurImageApi.class);
    private final ThreadsApiConfig config;
    private final String urlBase;

    public ImgurImageApi() {
        this.config = ThreadsApiConfig.getInstance();
        this.urlBase = this.config.getProperty("url_base_imgur");
    }
    
    protected void logException(Exception e) {
        log.error("Error: " + e.getMessage());
        if (e.getCause() != null) {
            log.error("Causa: " + e.getMessage());
        }
    }
    
    public ImgurTokenResponse refreshToken(String accessToken, String refreshToken) {
        ResteasyClient client = (ResteasyClient)ResteasyClientBuilder.newBuilder().build();
        Response response = null;
        String endpoint = this.config.getProperty("imgur_token_endpoint");
        int okStatus = Integer.parseInt(this.config.getProperty("imgur_token_valor_ok"));
        String clientId = this.config.getProperty("imgur_app_client_id");
        String clientSecret = this.config.getProperty("imgur_app_client_secret");
        
        try {
            String url = endpoint;
            WebTarget target = client.target(url)
                    .register(MultipartFormDataWriter.class);
            
            MultipartFormDataOutput formDataOutput = new MultipartFormDataOutput();
            formDataOutput.addFormData("refresh_token", refreshToken, MediaType.TEXT_PLAIN_TYPE);
            formDataOutput.addFormData("client_id", clientId, MediaType.TEXT_PLAIN_TYPE);
            formDataOutput.addFormData("client_secret", clientSecret, MediaType.TEXT_PLAIN_TYPE);
            formDataOutput.addFormData("grant_type", "refresh_token", MediaType.TEXT_PLAIN_TYPE);
            
            response = target.request()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .post(Entity.entity(formDataOutput, MediaType.MULTIPART_FORM_DATA));
            
            String jsonStr = response.readEntity(String.class);
            if (response.getStatus() != okStatus) {
                log.error(String.format("Respuesta del error %d. Detalle: %s", response.getStatus(), jsonStr));
                String str = "Error ejecutando: " + url + ". Se obtuvo el código de error: " + response.getStatus();
                throw new ImgurApiException(ImgurImageApi.class.getName(), str);
            }
            
            log.info("Se ejecutó la query: " + url);
            log.info("Respuesta recibida: " + jsonStr);
            
            Gson gson = new Gson();
            return gson.fromJson(jsonStr, ImgurTokenResponse.class);
        } catch (JsonSyntaxException e) {
            logException(e);
            throw  e;
        } catch (ImgurApiException e) {
            throw  e;
        } finally {
            if (response != null) {
                response.close();
            }
            client.close();
        }
    }
    
    public ImgurImageUpload uploadImage(String imagePath, String title, String description) throws Exception {
        ResteasyClient client = (ResteasyClient)ResteasyClientBuilder.newBuilder().build();
        Response response = null;
        String endpoint = this.config.getProperty("imageUpload_endpoint");
        int okStatus = Integer.parseInt(this.config.getProperty("imageUpload_valor_ok"));
        
        try {
            String accessToken = TokenManager.getImgurAccessToken();
            
            String url = this.urlBase + "/" + endpoint;
            WebTarget target = client.target(url)
                    .register(MultipartFormDataWriter.class);
            
            MultipartFormDataOutput formDataOutput = new MultipartFormDataOutput();
            File imageFile = new File(imagePath);
            InputStream fileStream = new FileInputStream(imageFile);
            formDataOutput.addFormData("image", fileStream, MediaType.APPLICATION_OCTET_STREAM_TYPE, imageFile.getName());
            formDataOutput.addFormData("type", "image", MediaType.TEXT_PLAIN_TYPE);
            formDataOutput.addFormData("title", title, MediaType.TEXT_PLAIN_TYPE);
            formDataOutput.addFormData("description", description, MediaType.TEXT_PLAIN_TYPE);
            
            response = target.request()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .post(Entity.entity(formDataOutput, MediaType.MULTIPART_FORM_DATA));
            
            String jsonStr = response.readEntity(String.class);
            if (response.getStatus() != okStatus) {
                log.error(String.format("Respuesta del error %d. Detalle: %s", response.getStatus(), jsonStr));
                String str = "Error ejecutando: " + url + ". Se obtuvo el código de error: " + response.getStatus();
                throw new ImgurApiException(ImgurImageApi.class.getName(), str);
            }
            
            log.debug("Se ejecutó la query: " + url);
            log.debug("Respuesta recibida: " + jsonStr);
            
            Gson gson = new Gson();
            return gson.fromJson(jsonStr, ImgurImageUpload.class);
        } catch (JsonSyntaxException | FileNotFoundException e) {
            logException(e);
            throw  e;
        } catch (ImgurApiException e) {
            throw  e;
        } finally {
            if (response != null) {
                response.close();
            }
            client.close();
        }
    }
}