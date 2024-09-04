package com.laboratorio.threadsapiinterface.imgur.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 03/09/2024
 * @updated 04/09/2024
 */

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ImgurTokenResponse {
    private String access_token;
    private int expires_in;
    private String bearer;
    private String scope;
    private String refresh_token;
    private Integer account_id;
    private String account_username;
}