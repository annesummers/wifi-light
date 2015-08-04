package com.giganticsheep.wifilight.api.network.error;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 30/07/15. <p>
 * (*_*)
 */
public class WifiLightAPIException extends WifiLightException {
    public WifiLightAPIException(final WifiLightError error) {
        super(error);
    }

    public WifiLightAPIException() {

    }
}
