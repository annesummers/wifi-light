package com.giganticsheep.wifilight.api.model;

import android.support.annotation.NonNull;

/**
 * Created by anne on 13/07/15.
 * (*_*)
 */
public class ColourData {
    public double hue;
    public double saturation;
    public int kelvin;

    @NonNull
    @Override
    public String toString() {
        return "ColorData{" +
                "hue=" + hue +
                ", saturation=" + saturation +
                ", kelvin=" + kelvin +
                '}';
    }
}
