package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.LightStatus;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
class StatusResponse extends Response implements LightStatus {

    public String status;

    public StatusResponse(final String status) {
        this.status = status;
    }

    public StatusResponse() { }

    /**
     * @return the status as a Status enum
     */
    public final LightControl.Status getStatus() {
        return LightControl.Status.parse(status);
    }

    @NonNull
    @Override
    public String toString() {
        return "LightStatus{" +
                "status='" + status + '\'' +
                '}';
    }
}
