package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.api.model.WifiLightData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
class Response implements Serializable, WifiLightData {
    @SerializedName("cod")
    public int httpCode;

    public String id;
    public String label;

    public Response(String id) {
        this.id = id;
    }

    public Response() { }

    @Override
    public final String id() {
        return id;
    }

    @Override
    public final String getName() {
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
