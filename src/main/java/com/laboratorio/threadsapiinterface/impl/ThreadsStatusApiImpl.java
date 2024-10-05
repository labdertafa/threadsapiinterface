package com.laboratorio.threadsapiinterface.impl;

import com.google.gson.JsonSyntaxException;
import com.laboratorio.clientapilibrary.exceptions.ApiClientException;
import com.laboratorio.clientapilibrary.model.ApiMethodType;
import com.laboratorio.clientapilibrary.model.ApiRequest;
import com.laboratorio.clientapilibrary.model.ApiResponse;
import com.laboratorio.threadsapiinterface.ThreadsStatusApi;
import com.laboratorio.threadsapiinterface.imgur.ImgurImageApi;
import com.laboratorio.threadsapiinterface.imgur.model.ImgurImageUpload;
import static com.laboratorio.threadsapiinterface.impl.ThreadsBaseApi.log;
import com.laboratorio.threadsapiinterface.model.ThreadsPost;
import com.laboratorio.threadsapiinterface.model.ThreadsPostResponse;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 03/09/2024
 * @updated 05/10/2024
 */
public class ThreadsStatusApiImpl extends ThreadsBaseApi implements ThreadsStatusApi {
    public ThreadsStatusApiImpl(String accessToken) {
        super(accessToken);
    }

    @Override
    public ThreadsPost retrievePost(String id) {
        int okStatus = Integer.parseInt(this.config.getProperty("retrievePost_ok_status"));
        
        try {
            String url = this.urlBase + "/" + id;
            ApiRequest request = new ApiRequest(url, okStatus, ApiMethodType.GET);
            request.addApiPathParam("fields", "id,media_product_type,media_type,media_url,permalink,owner,username,text,timestamp,shortcode,thumbnail_url,children,is_quote_post");
            request.addApiHeader("Authorization", "Bearer " + this.accessToken);
            
            ApiResponse response = this.client.executeApiRequest(request);
            
            return this.gson.fromJson(response.getResponseStr(), ThreadsPost.class);
        } catch (JsonSyntaxException e) {
            logException(e);
            throw e;
        } catch (ApiClientException e) {
            throw e;
        }
    }
    
    private ThreadsPostResponse executePostStatus(String text, String imageUrl) {
        String endpoint = this.config.getProperty("post_endpoint");
        int okStatus = Integer.parseInt(this.config.getProperty("post_valor_ok"));
        String userId = this.config.getProperty("threads_user_id");
        String mediaType = "TEXT";
        
        if (imageUrl != null) {
            mediaType = "IMAGE";
        }
        
        try {
            String url = this.urlBase + "/" + userId + "/" + endpoint;
            ApiRequest request = new ApiRequest(url, okStatus, ApiMethodType.POST);
            request.addApiPathParam("text", text);
            request.addApiPathParam("media_type", mediaType);
            if (imageUrl != null) {
                request.addApiPathParam("image_url", imageUrl);
            }
            request.addApiPathParam("access_token", this.accessToken);
            
            ApiResponse response = this.client.executeApiRequest(request);
            
            return this.gson.fromJson(response.getResponseStr(), ThreadsPostResponse.class);
        } catch (JsonSyntaxException e) {
            logException(e);
            throw e;
        } catch (ApiClientException e) {
            throw e;
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
                log.debug("La imagen se ha subido correctamente a Imgur: " + imageUpload.getData().getLink());
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
        String endpoint = this.config.getProperty("publish_endpoint");
        int okStatus = Integer.parseInt(this.config.getProperty("publish_valor_ok"));
        String userId = this.config.getProperty("threads_user_id");
        
        try {
            String url = this.urlBase + "/" + userId + "/" + endpoint;
            ApiRequest request = new ApiRequest(url, okStatus, ApiMethodType.POST);
            request.addApiPathParam("creation_id", id);
            request.addApiPathParam("access_token", this.accessToken);
            
            ApiResponse response = this.client.executeApiRequest(request);

            return this.gson.fromJson(response.getResponseStr(), ThreadsPostResponse.class);
        } catch (JsonSyntaxException e) {
            logException(e);
            throw e;
        } catch (ApiClientException e) {
            throw e;
        }
    }    
}