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
 * @version 1.0
 * @created 04/09/2024
 * @updated 04/09/2024
 */

@Getter @Setter @AllArgsConstructor
public class ImgurTokenInfo implements Serializable {
    private String access_token;
    private LocalDateTime expirationDate;
    private String bearer;
    private String refresh_token;

    public ImgurTokenInfo(ImgurTokenResponse response) {
        this.access_token = response.getAccess_token();
        this.expirationDate = LocalDateTime.now().plusSeconds(response.getExpires_in());
        this.bearer = response.getBearer();
        this.refresh_token = response.getRefresh_token();
    }
}