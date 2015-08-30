package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.base.error.WifiLightError;

import java.util.ArrayList;
import java.util.List;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 30/07/15. <p>
 * (*_*)
 */
public class ErrorResponse implements WifiLightError {

    String message;
    Errors errors;

    class Errors {
        List<String> color;
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
