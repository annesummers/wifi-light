package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.api.network.error.WifiLightError;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 30/07/15. <p>
 * (*_*)
 */
public class ErrorResponse implements
        WifiLightError {
    @SerializedName("cod")
    int httpCode;

    // error fields
    String message;
    Errors errors;

    class Errors {
        List<String> color;
    }

    @Override
    public int getHttpCode() {
        return httpCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public List<String> getErrors() {
        if(errors != null) {
            return errors.color;
        }

        return new ArrayList<>();
    }
}
