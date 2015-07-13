package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.GroupData;
import com.giganticsheep.wifilight.api.model.Light;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
class LightResponse extends Response
                            implements Light,
                            Serializable {

    private static final int BRIGHTNESS_MAX = 100;
    private static final int SATURATION_MAX = 100;

    public String uuid;
    public boolean connected;
    public String power = "on";
    public double brightness;
    public String product_name;
    public String last_seen;
    public double seconds_since_last_seen;

    public ColourData color;
    public GroupData location;
    public GroupData group;
    public CapabilitiesData capabilities;

    public LightResponse(String id) {
        super(id);

        color = new ColourData();
        location = new GroupData();
        group = new GroupData();
        capabilities = new CapabilitiesData();
    }

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ", Locale.US);

    public boolean isConnected() {
        return connected;
    }

    public int getHue() {
        return convertHue(color.hue);
    }

    public int getSaturation() {
        return convertSaturation(color.saturation);
    }

    public int getKelvin() {
        return color.kelvin;
    }

    public int getBrightness() {
        return convertBrightness(brightness);
    }

    public LightControl.Power getPower() {
        return power.equals("on") ? LightControl.Power.ON : LightControl.Power.OFF;
    }

    public String getProductName() {
        return product_name;
    }

    public Date getLastSeen() {
        Date date = new Date();
        try {
            date = dateFormat.parse(last_seen);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public double getSecondsSinceLastSeen() {
        return seconds_since_last_seen;
    }

    public boolean hasColour() {
        return capabilities.has_color;
    }

    public boolean hasVariableColourTemp() {
        return capabilities.has_variable_color_temp;
    }

    // conversion methods

    public static int convertBrightness(double brightness) {
        return (int) (brightness*BRIGHTNESS_MAX);
    }

    public static int convertSaturation(double saturation) {
        return (int)(saturation*SATURATION_MAX);
    }

    public static int convertHue(double hue) {
        return (int) hue;
    }

    public static double convertBrightness(int brightness) {
        return (double)brightness/BRIGHTNESS_MAX;
    }

    public static double convertSaturation(int saturation) {
        return (double)saturation/SATURATION_MAX;
    }

    public static double convertHue(int hue) {
        return (double) hue;
    }

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

    public class CapabilitiesData {
        public boolean has_color;
        public boolean has_variable_color_temp;

        @Override
        public String toString() {
            return "CapabilitiesData{" +
                    "has_color=" + has_color +
                    ", has_variable_color_temp=" + has_variable_color_temp +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LightDataResponse{" +
                "uuid='" + uuid + '\'' +
                ", connected=" + connected +
                ", power='" + power + '\'' +
                ", brightness=" + brightness +
                ", product_name='" + product_name + '\'' +
                ", last_seen='" + last_seen + '\'' +
                ", seconds_since_last_seen=" + seconds_since_last_seen +
                ", color=" + color +
                ", location=" + location +
                ", group=" + group +
                ", capabilities=" + capabilities +
                '}';
    }
}
