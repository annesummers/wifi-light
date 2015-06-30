package com.giganticsheep.wifilight.api.model;

import com.giganticsheep.wifilight.api.ModelConstants;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
public class Light extends LightResponse implements Serializable {

    public static int KELVIN_BASE = 2500;

    public String uuid;
    public boolean connected;
    public String power;
    public double brightness;
    public String product_name;
    public String last_seen;
    public double seconds_since_last_seen;

    public ColorData color;
    public GroupData location;
    public GroupData group;
    public CapabilitiesData capabilities;

    public Light(String id) {
        super(id);
    }

    public class ColorData {
        public double hue;
        public double saturation;
        public int kelvin;
    }

    public class CapabilitiesData {
        public boolean has_color;
        public boolean has_variable_color_temp;
    }

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");

    /**
     * @return if this light is connected or not
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * @return the hue of this light
     */
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

    public ModelConstants.Power getPower() {
        return power.equals("on") ? ModelConstants.Power.ON : ModelConstants.Power.OFF;
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

    public static int convertBrightness(double brightness) {
        return (int) (brightness*100);
    }

    public static int convertSaturation(double saturation) {
        return (int)(saturation*100);
    }

    public static int convertHue(double hue) {
        return (int) hue;
    }
    public static double convertBrightness(int brightness) {
        return (double)brightness/100;
    }

    public static double convertSaturation(int saturation) {
        return (double)saturation/100;
    }

    public static double convertHue(int hue) {
        return (double) hue;
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
