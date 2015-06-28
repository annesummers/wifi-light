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
public class LightDataResponse extends LightResponse implements Serializable {

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
        return (int)color.hue;
    }

    public int getSaturation() {
        return (int)(color.saturation*100);
    }

    public int getKelvin() {
        return color.kelvin;
    }

    public int getBrightness() {
        return (int) (brightness*100);
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
