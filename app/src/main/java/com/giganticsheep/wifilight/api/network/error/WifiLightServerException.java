package com.giganticsheep.wifilight.api.network.error;

import com.giganticsheep.wifilight.base.error.WifiLightError;
import com.giganticsheep.wifilight.base.error.WifiLightException;

import org.jetbrains.annotations.Nullable;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 30/07/15. <p>
 * (*_*)
 */
public class WifiLightServerException extends WifiLightException {

    public WifiLightServerException(@Nullable final String reason,
                                    @Nullable final WifiLightError error) {
        super(reason, error);
    }

    public WifiLightServerException() { }
}
