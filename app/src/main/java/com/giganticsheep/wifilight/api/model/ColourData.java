package com.giganticsheep.wifilight.api.model;

/**
 * Created by anne on 13/07/15.
 * (*_*)
 */
public class ColourData {
    public double hue;
    public double saturation;
    public int kelvin;

    @Override
    public String toString() {
        return "ColorData{" +
                "hue=" + hue +
                ", saturation=" + saturation +
                ", kelvin=" + kelvin +
                '}';
    }
}
