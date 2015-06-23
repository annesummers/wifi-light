package com.giganticsheep.wifilight.model;

import org.jetbrains.annotations.NonNls;

/**
 * Created by anne on 23/06/15.
 * (*_*)
 */
public class ModelConstants {

    public enum Power {
        ON ("on"),
        OFF ("off");

        private final String mName;

        Power(String name) {
            mName = name;
        }

        public String powerString() {
            return mName;
        }
    }

    @NonNls static final String URL_ALL = "all";
    @NonNls static final String URL_DURATION = "duration";
    @NonNls static final String URL_POWER_ON = "power_on";
    @NonNls static final String URL_COLOUR = "color";
    @NonNls static final String URL_STATE = "state";

    @NonNls static final String LABEL_BEARER = "Bearer";

    @NonNls static final String LABEL_HUE = "hue:";
    @NonNls static final String LABEL_SATURATION = "saturation:";
    @NonNls static final String LABEL_KELVIN = "kelvin:";
    @NonNls static final String LABEL_BRIGHTNESS = "brightness:";

    @NonNls static final char SPACE = ' ';
}
