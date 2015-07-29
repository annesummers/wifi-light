package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.api.model.WifiLightData;
import com.google.gson.annotations.SerializedName;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
class Response implements WifiLightData {
    @SerializedName("cod")
    public int httpCode;

    public String id;
    public String label;

    protected Response(String id) {
        this.id = id;
    }

    public Response() { }

    @Override
    public final String getId() {
        return id;
    }

    public final String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "LightResponse{" +
                "httpCode=" + httpCode +
                ", id='" + id + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
