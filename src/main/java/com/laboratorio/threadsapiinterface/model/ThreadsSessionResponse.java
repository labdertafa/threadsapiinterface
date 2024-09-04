package com.laboratorio.threadsapiinterface.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 03/09/2024
 * @updated 03/09/2024
 */

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ThreadsSessionResponse {
    private String access_token;
    private String token_type;
    private int expires_in;
}