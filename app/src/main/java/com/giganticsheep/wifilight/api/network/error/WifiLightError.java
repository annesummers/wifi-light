package com.giganticsheep.wifilight.api.network.error;

import java.util.List;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 30/07/15. <p>
 * (*_*)
 */
public interface WifiLightError {

    int getHttpCode();
    String getMessage();
    List<String> getErrors();
}
