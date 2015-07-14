package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
public class StatusResponse extends Response {
    public String status;

    public StatusResponse(String status) {
        this.status = status;
    }

    public StatusResponse() {
        this.status = "";
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    @NonNull
    @Override
    public String toString() {
        return "StatusResponse{" +
                "status='" + status + '\'' +
                '}';
    }
}
