package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.ColourData;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightConstants;

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

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public int getHue() {
        return LightConstants.convertHue(color.hue);
    }

    @Override
    public int getSaturation() {
        return LightConstants.convertSaturation(color.saturation);
    }

    @Override
    public int getKelvin() {
        return color.kelvin;
    }

    @Override
    public int getBrightness() {
        return LightConstants.convertBrightness(brightness);
    }

    @Override
    public LightControl.Power getPower() {
        return power.equals("on") ? LightControl.Power.ON : LightControl.Power.OFF;
    }

    @Override
    public String getProductName() {
        return product_name;
    }

    @Override
    public Date getLastSeen() {
        Date date = new Date();
        try {
            date = dateFormat.parse(last_seen);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    @Override
    public double getSecondsSinceLastSeen() {
        return seconds_since_last_seen;
    }

    @Override
    public boolean hasColour() {
        return capabilities.has_color;
    }

    @Override
    public boolean hasVariableColourTemp() {
        return capabilities.has_variable_color_temp;
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