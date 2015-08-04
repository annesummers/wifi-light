package com.giganticsheep.wifilight.api.network.error;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/08/15. <p>
 * (*_*)
 */
public class WifiLightException extends Exception {
    private WifiLightError error;

    public WifiLightException(WifiLightError error) {
        this.error = error;
    }

    public WifiLightException() {

    }

    @Override
    public String getMessage() {
        String errors = "";

        if(error != null) {

            for (String s : error.getErrors()) {
                errors += " ";
                errors += s;
            }

            return error.getMessage() + errors;
        }

        return errors;
    }
}
