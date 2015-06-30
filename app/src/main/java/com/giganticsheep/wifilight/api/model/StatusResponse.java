package com.giganticsheep.wifilight.api.model;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
public class StatusResponse extends LightResponse {
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

    @Override
    public String toString() {
        return "StatusResponse{" +
                "status='" + status + '\'' +
                '}';
    }
}
