package com.laboratorio.threadsapiinterface.imgur.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 03/09/2024
 * @updated 03/09/2024
 */

@Getter @Setter @AllArgsConstructor
public class ImgurTokenRequest {
    private String refresh_token;
    private String cliente_id;
    private String client_secret;
    private String grant_type;

    public ImgurTokenRequest(String refresh_token, String cliente_id, String client_secret) {
        this.refresh_token = refresh_token;
        this.cliente_id = cliente_id;
        this.client_secret = client_secret;
        this.grant_type = "refresh_token";
    }
}