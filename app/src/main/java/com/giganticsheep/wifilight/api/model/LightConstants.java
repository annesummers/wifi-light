package com.giganticsheep.wifilight.api.model;

/**
 * Created by anne on 13/07/15.
 * (*_*)
 */
public class LightConstants {

    public static final int KELVIN_BASE = 2500;

    private static final int BRIGHTNESS_MAX = 100;
    private static final int SATURATION_MAX = 100;

    public static int convertBrightness(double brightness) {
        return (int) (brightness*BRIGHTNESS_MAX);
    }

    public static int convertSaturation(double saturation) {
        return (int)(saturation*SATURATION_MAX);
    }

    public static int convertHue(double hue) {
        return (int) hue;
    }

    static double convertBrightness(int brightness) {
        return (double)brightness/BRIGHTNESS_MAX;
    }

    static double convertSaturation(int saturation) {
        return (double)saturation/SATURATION_MAX;
    }

    static double convertHue(int hue) {
        return (double) hue;
    }
}
