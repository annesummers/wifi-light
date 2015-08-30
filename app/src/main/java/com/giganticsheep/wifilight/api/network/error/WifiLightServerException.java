package com.giganticsheep.wifilight.api.network.error;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 30/07/15. <p>
 * (*_*)
 */
public class WifiLightServerException extends WifiLightException {

    public WifiLightServerException(String reason, WifiLightError error) {
        super(reason, error);
    }

    public WifiLightServerException() {

    }
}
