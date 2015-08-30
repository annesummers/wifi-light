package com.giganticsheep.wifilight.util;

import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.base.error.ErrorStrings;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 30/08/15. <p>
 * (*_*)
 */
public class AndroidErrorStrings implements ErrorStrings {

    private final Resources resources;

    public AndroidErrorStrings(@NonNull final Resources resources) {
        this.resources = resources;
    }

    @Override
    public String apiErrorString() {
        return resources.getString(R.string.error_api_error);
    }

    @Override
    public String networkErrorString() {
        return resources.getString(R.string.error_network_error);
    }

    @Override
    public String serverErrorString() {
        return resources.getString(R.string.error_server_error);
    }

    @Override
    public String generalErrorString() {
        return resources.getString(R.string.error_general_error);
    }
}
