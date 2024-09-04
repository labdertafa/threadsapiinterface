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
 * @updated 03/09/2024
 */

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ImgurData {
    private String id;
    private String deletehash;
    private Integer account_id;
    private String account_url;
    private String title;
    private String description;
    private String name;
    private String type;
    private int width;
    private int height;
    private int size;
    private int views;
    private int bandwidth;
    private boolean animated;
    private boolean favorite;
    private boolean in_gallery;
    private boolean in_most_viral;
    private boolean has_sound;
    private boolean is_ad;
    private String link;
    private long datetime;
    private String mp4;
    private String hls;
}