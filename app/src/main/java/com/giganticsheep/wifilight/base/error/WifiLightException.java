package com.giganticsheep.wifilight.base.error;

import hugo.weaving.DebugLog;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/08/15. <p>
 * (*_*)
 */
public class WifiLightException extends Exception {

    private String errorString = "";
    private WifiLightError error;

    public WifiLightException(final String reason,
                              final WifiLightError error) {
        this.errorString = reason != null ? reason : "";
        this.error = error;
    }

    public WifiLightException() { }

    public WifiLightException(String reason) {
        this.errorString = reason != null ? reason : "";
    }

    @DebugLog
    @Override
    public String getMessage() {
        StringBuilder stringErrors = new StringBuilder();

        if(error != null) {
            for (String s : error.getErrors()) {
                stringErrors.append(" ");
                stringErrors.append(s);
            }

            stringErrors.insert(0, error.getMessage());
            if(errorString != null) {
                stringErrors.insert(0, ": ");
            }
        }

        if(errorString != null){
            stringErrors.insert(0, errorString);
        }

        return stringErrors.toString();
    }
}
