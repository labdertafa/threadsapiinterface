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
public class ThreadsPost {
    private String id;
    private String media_product_type;
    private String media_type;
    private String media_url;
    private String permalink;
    private ThreadsOwner owner;
    private String username;
    private String text;
    private String timestamp;
    private String shortcode;
    private String thumbnail_url;
    private boolean is_quote_post;
}