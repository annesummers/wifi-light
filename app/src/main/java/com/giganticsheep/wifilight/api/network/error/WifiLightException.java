package com.giganticsheep.wifilight.api.network.error;

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

    @Override
    public String getMessage() {
        String errors = "";

        if(error != null) {
            for (String s : error.getErrors()) {
                errors += " ";
                errors += s;
            }

            return errorString + " " + error.getMessage() + errors;
        }

        return errors;
    }
}
