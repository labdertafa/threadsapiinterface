package com.laboratorio.threadsapiinterface.imgur.util;

import com.laboratorio.threadsapiinterface.imgur.model.ImgurTokenResponse;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.2
 * @created 04/09/2024
 * @updated 30/12/2025
 */

@Getter @Setter @AllArgsConstructor
public class ImgurTokenInfo implements Serializable {
    private static final long serialVersionUID = 4730417404333203599L;
    
    private String accessToken;
    private LocalDateTime expirationDate;
    private String bearer;
    private String refreshToken;

    public ImgurTokenInfo(ImgurTokenResponse response) {
        this.accessToken = response.getAccess_token();
        this.expirationDate = LocalDateTime.now().plusSeconds(response.getExpires_in());
        this.bearer = response.getBearer();
        this.refreshToken = response.getRefresh_token();
    }
}