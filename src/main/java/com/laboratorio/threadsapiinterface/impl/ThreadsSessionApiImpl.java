package com.laboratorio.threadsapiinterface.impl;

import com.google.gson.JsonSyntaxException;
import com.laboratorio.clientapilibrary.exceptions.ApiClientException;
import com.laboratorio.clientapilibrary.model.ApiMethodType;
import com.laboratorio.clientapilibrary.model.ApiRequest;
import com.laboratorio.clientapilibrary.model.ApiResponse;
import com.laboratorio.threadsapiinterface.ThreadsSessionApi;
import com.laboratorio.threadsapiinterface.exception.ThreadsApiException;
import com.laboratorio.threadsapiinterface.model.ThreadsSessionResponse;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 03/09/2024
 * @updated 01/05/2025
 */
public class ThreadsSessionApiImpl extends ThreadsBaseApi implements ThreadsSessionApi {
    public ThreadsSessionApiImpl(String accessToken) {
        super(accessToken, "");
    }
    
    @Override
    public ThreadsSessionResponse exchangeToken() {
        String endpoint = this.config.getProperty("exchangeToken_endpoint");
        int okStatus = Integer.parseInt(this.config.getProperty("exchangeToken_ok_status"));
        String appClientSecret = this.config.getProperty("app_client_secret");
        String grantType = this.config.getProperty("exchangeToken_grant_type");
        
        try {
            String url = this.urlBase + "/" + endpoint;
            ApiRequest request = new ApiRequest(url, okStatus, ApiMethodType.GET);
            request.addApiPathParam("client_secret", appClientSecret);
            request.addApiPathParam("grant_type", grantType);
            request.addApiPathParam("access_token", this.accessToken);
            
            ApiResponse response = this.client.executeApiRequest(request);

            return this.gson.fromJson(response.getResponseStr(), ThreadsSessionResponse.class);
        } catch (JsonSyntaxException e) {
            logException(e);
            throw e;
        } catch (ApiClientException e) {
            throw e;
        } catch (Exception e) {
            throw new ThreadsApiException(ThreadsSessionApiImpl.class.getName(), "No se pudo intercambiar el token Threads", e);
        }
    }

    @Override
    public ThreadsSessionResponse refreshSession() {
        String endpoint = this.config.getProperty("refreshSession_endpoint");
        int okStatus = Integer.parseInt(this.config.getProperty("refreshSession_ok_status"));
        String grantType = this.config.getProperty("refreshSession_grant_type");
        
        try {
            String url = this.urlBase + "/" + endpoint;
            ApiRequest request = new ApiRequest(url, okStatus, ApiMethodType.GET);
            request.addApiPathParam("grant_type", grantType);
            request.addApiPathParam("access_token", this.accessToken);
            
            ApiResponse response = this.client.executeApiRequest(request);
            
            log.debug("Response: {}", response.getResponseStr());

            return this.gson.fromJson(response.getResponseStr(), ThreadsSessionResponse.class);
        } catch (JsonSyntaxException e) {
            logException(e);
            throw e;
        } catch (ApiClientException e) {
            throw e;
        } catch (Exception e) {
            throw new ThreadsApiException(ThreadsSessionApiImpl.class.getName(), "No se pudo renovar el token Threads", e);
        }
    }
}