package com.giganticsheep.wifilight.api.network;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */
public class NetworkDetails {

    private String apiKey;
    private String baseURL1;
    private String baseURL2;

    public NetworkDetails(String apiKey,
                          String baseURL1,
                          String baseURL2) {
        this.apiKey = apiKey;
        this.baseURL1 = baseURL1;
        this.baseURL2 = baseURL2;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseURL1() {
        return baseURL1;
    }

    public String getBaseURL2() {
        return baseURL2;
    }
}
