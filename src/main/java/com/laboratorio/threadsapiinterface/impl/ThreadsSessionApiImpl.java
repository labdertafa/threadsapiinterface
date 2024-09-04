package com.laboratorio.threadsapiinterface.impl;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.laboratorio.threadsapiinterface.ThreadsSessionApi;
import com.laboratorio.threadsapiinterface.exception.ThreadsApiException;
import com.laboratorio.threadsapiinterface.model.ThreadsSessionResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 03/09/2024
 * @updated 03/09/2024
 */
public class ThreadsSessionApiImpl extends ThreadsBaseApi implements ThreadsSessionApi {
    public ThreadsSessionApiImpl(String accessToken) {
        super(accessToken);
    }
    
    @Override
    public ThreadsSessionResponse exchangeToken() {
        Client client = ClientBuilder.newClient();
        Response response = null;
        String endpoint = this.config.getProperty("exchangeToken_endpoint");
        int okStatus = Integer.parseInt(this.config.getProperty("exchangeToken_ok_status"));
        String appClientSecret = this.config.getProperty("app_client_secret");
        String grantType = this.config.getProperty("exchangeToken_grant_type");
        
        try {
            String url = this.urlBase + "/" + endpoint;
            WebTarget target = client.target(url)
                    .queryParam("client_secret", appClientSecret)
                    .queryParam("grant_type", grantType)
                    .queryParam("access_token", this.accessToken);
            
            response = target.request(MediaType.APPLICATION_JSON)
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
            return gson.fromJson(jsonStr, ThreadsSessionResponse.class);
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
    public ThreadsSessionResponse refreshSession() {
        Client client = ClientBuilder.newClient();
        Response response = null;
        String endpoint = this.config.getProperty("refreshSession_endpoint");
        int okStatus = Integer.parseInt(this.config.getProperty("refreshSession_ok_status"));
        String grantType = this.config.getProperty("refreshSession_grant_type");
        
        try {
            String url = this.urlBase + "/" + endpoint;
            WebTarget target = client.target(url)
                    .queryParam("grant_type", grantType)
                    .queryParam("access_token", this.accessToken);
            
            response = target.request(MediaType.APPLICATION_JSON)
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
            return gson.fromJson(jsonStr, ThreadsSessionResponse.class);
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