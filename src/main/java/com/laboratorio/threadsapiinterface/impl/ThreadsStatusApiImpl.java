package com.laboratorio.threadsapiinterface.impl;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.laboratorio.threadsapiinterface.ThreadsStatusApi;
import com.laboratorio.threadsapiinterface.exception.ThreadsApiException;
import com.laboratorio.threadsapiinterface.imgur.ImgurImageApi;
import com.laboratorio.threadsapiinterface.imgur.model.ImgurImageUpload;
import static com.laboratorio.threadsapiinterface.impl.ThreadsBaseApi.log;
import com.laboratorio.threadsapiinterface.model.ThreadsPost;
import com.laboratorio.threadsapiinterface.model.ThreadsPostResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 03/09/2024
 * @updated 04/09/2024
 */
public class ThreadsStatusApiImpl extends ThreadsBaseApi implements ThreadsStatusApi {
    public ThreadsStatusApiImpl(String accessToken) {
        super(accessToken);
    }

    @Override
    public ThreadsPost retrievePost(String id) {
        Client client = ClientBuilder.newClient();
        Response response = null;
        int okStatus = Integer.parseInt(this.config.getProperty("retrievePost_ok_status"));
        
        try {
            String url = this.urlBase + "/" + id;
            WebTarget target = client.target(url)
                    .queryParam("fields", "id,media_product_type,media_type,media_url,permalink,owner,username,text,timestamp,shortcode,thumbnail_url,children,is_quote_post");
            
            response = target.request(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.accessToken)
                    .get();
            
            String jsonStr = response.readEntity(String.class);
            if (response.getStatus() != okStatus) {
                log.error(String.format("Respuesta del error %d: %s", response.getStatus(), jsonStr));
                String str = "Error ejecutando: " + url + ". Se obtuvo el código de error: " + response.getStatus();
                throw new ThreadsApiException(ThreadsSessionApiImpl.class.getName(), str);
            }
            
            log.debug("Se ejecutó la query: " + url);
            log.info("Respuesta recibida: " + jsonStr);
            
            Gson gson = new Gson();
            return gson.fromJson(jsonStr, ThreadsPost.class);
        } catch (JsonSyntaxException e) {
            logException(e);
            throw e;
        } catch (ThreadsApiException e) {
            throw e;
        } finally {
            if (response != null) {
                response.close();
            }
            client.close();
        }
    }
    
    private ThreadsPostResponse executePostStatus(String text, String imageUrl) {
        Client client = ClientBuilder.newClient();
        Response response = null;
        String endpoint = this.config.getProperty("post_endpoint");
        int okStatus = Integer.parseInt(this.config.getProperty("post_valor_ok"));
        String userId = this.config.getProperty("threads_user_id");
        String mediaType = "TEXT";
        
        if (imageUrl != null) {
            mediaType = "IMAGE";
        }
        
        try {
            String url = this.urlBase + "/" + userId + "/" + endpoint;
            WebTarget target = client.target(url)
                    .queryParam("text", text)
                    .queryParam("media_type", mediaType);
            if (imageUrl != null) {
                target = target.queryParam("image_url", imageUrl);
            }
            
            response = target.request(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.accessToken)
                    .post(Entity.text(""));
            
            String jsonStr = response.readEntity(String.class);
            if (response.getStatus() != okStatus) {
                log.error(String.format("Respuesta del error %d: %s", response.getStatus(), jsonStr));
                String str = "Error ejecutando: " + url + ". Se obtuvo el código de error: " + response.getStatus();
                throw new ThreadsApiException(ThreadsSessionApiImpl.class.getName(), str);
            }
            
            log.debug("Se ejecutó la query: " + url);
            log.info("Respuesta recibida: " + jsonStr);
            
            Gson gson = new Gson();
            return gson.fromJson(jsonStr, ThreadsPostResponse.class);
        } catch (JsonSyntaxException e) {
            logException(e);
            throw e;
        } catch (ThreadsApiException e) {
            throw e;
        } finally {
            if (response != null) {
                response.close();
            }
            client.close();
        }
    }

    @Override
    public ThreadsPostResponse postStatus(String text) {
        return this.executePostStatus(text, null);
    }

    @Override
    public ThreadsPostResponse postStatus(String text, String imagePath) {
        String imageUrl = null;
        
        try {
            ImgurImageApi imageApi = new ImgurImageApi();
            ImgurImageUpload imageUpload = imageApi.uploadImage(imagePath, "N/A", "N/A");
            if (imageUpload.isSuccess()) {
                log.info("La imagen se ha subido correctamente a Imgur: " + imageUpload.getData().getLink());
                imageUrl = imageUpload.getData().getLink();
            }
        } catch (Exception e) {
            log.error("Ocurrió un error subiendo la imagen a Imgur");
            logException(e);
        }
        
        return this.executePostStatus(text, imageUrl);
    }

    @Override
    public ThreadsPostResponse publishStatus(String id) {
        Client client = ClientBuilder.newClient();
        Response response = null;
        String endpoint = this.config.getProperty("publish_endpoint");
        int okStatus = Integer.parseInt(this.config.getProperty("publish_valor_ok"));
        String userId = this.config.getProperty("threads_user_id");
        
        try {
            String url = this.urlBase + "/" + userId + "/" + endpoint;
            WebTarget target = client.target(url)
                    .queryParam("creation_id", id);
            
            response = target.request(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.accessToken)
                    .post(Entity.text(""));
            
            String jsonStr = response.readEntity(String.class);
            if (response.getStatus() != okStatus) {
                log.error(String.format("Respuesta del error %d: %s", response.getStatus(), jsonStr));
                String str = "Error ejecutando: " + url + ". Se obtuvo el código de error: " + response.getStatus();
                throw new ThreadsApiException(ThreadsSessionApiImpl.class.getName(), str);
            }
            
            log.debug("Se ejecutó la query: " + url);
            log.info("Respuesta recibida: " + jsonStr);
            
            Gson gson = new Gson();
            return gson.fromJson(jsonStr, ThreadsPostResponse.class);
        } catch (JsonSyntaxException e) {
            logException(e);
            throw e;
        } catch (ThreadsApiException e) {
            throw e;
        } finally {
            if (response != null) {
                response.close();
            }
            client.close();
        }
    }
    
}