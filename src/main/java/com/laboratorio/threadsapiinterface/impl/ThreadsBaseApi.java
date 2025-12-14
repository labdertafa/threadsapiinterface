package com.laboratorio.threadsapiinterface.impl;

import com.google.gson.Gson;
import com.laboratorio.clientapilibrary.ApiClient;
import com.laboratorio.clientapilibrary.utils.ReaderConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.3
 * @created 03/09/2024
 * @updated 14/12/2025
 */
public class ThreadsBaseApi {
    protected static final Logger log = LogManager.getLogger(ThreadsBaseApi.class);
    protected final ApiClient client;
    protected final String accessToken;
    protected final String userId;
    protected final ReaderConfig config;
    protected final String urlBase;
    protected final Gson gson;

    public ThreadsBaseApi(String accessToken, String userId) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.config = new ReaderConfig("config//threads_api.properties");
        this.urlBase = this.config.getProperty("url_base_threads");
        this.gson = new Gson();
        String proxyHost = this.config.getProperty("threads_proxy_host");
        String proxyPortStr = this.config.getProperty("threads_proxy_port");
        String certificatePath = this.config.getProperty("threads_proxy_certificate");
        if (proxyHost != null && !proxyHost.isBlank() && proxyPortStr != null && !proxyPortStr.isBlank()
                && certificatePath != null && !certificatePath.isBlank()) {
            int proxyPort = Integer.parseInt(proxyPortStr);
            this.client = new ApiClient(proxyHost, proxyPort, certificatePath);
        } else {
            this.client = new ApiClient();
        }
    }
}