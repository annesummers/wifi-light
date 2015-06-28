package com.giganticsheep.wifilight.api;

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
}
