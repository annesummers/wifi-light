package com.giganticsheep.wifilight.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
public class LightResponse implements Serializable {
    @SerializedName("cod")
    public int httpCode;

    public String id;
    public String label;

    public final String id() {
        return id;
    }

    /**
     *
     * @return the name of this light
     */
    public String getName() {
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
