package com.giganticsheep.wifilight.api.network.error;

import com.giganticsheep.wifilight.base.error.WifiLightError;
import com.giganticsheep.wifilight.base.error.WifiLightException;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 30/07/15. <p>
 * (*_*)
 */
public class WifiLightAPIException extends WifiLightException {
    public WifiLightAPIException(final String reason,
                                 final WifiLightError error) {
        super(reason, error);
    }

    public WifiLightAPIException() {

    }
}
