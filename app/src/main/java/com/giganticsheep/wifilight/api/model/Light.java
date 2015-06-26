package com.giganticsheep.wifilight.api.model;

import com.giganticsheep.wifilight.api.network.LightDataResponse;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.api.ModelConstants;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class Light extends WifiLightObject implements Serializable {

    private final String name;
    private boolean connected;
    private double hue;
    private double saturation;
    private int kelvin;
    private double brightness;

    private ModelConstants.Power power;

    private String productName;

    private Date lastSeen;
    private double secondsSinceLastSeen;

    private boolean hasColour;
    private boolean hasVariableColourTemp;

    private boolean enabled;

    // TODO date

    /**
     * @param name the name of this light
     */
    public Light(final String id, final String name) {
        super(id);

        this.name = name;
    }

    public Light(boolean enabled, LightDataResponse dataEnvelope) {
        super(dataEnvelope.id);

        this.enabled = enabled;

        name = dataEnvelope.label;
        connected = dataEnvelope.connected;
        power = dataEnvelope.power.equals("on") ? ModelConstants.Power.ON : ModelConstants.Power.OFF;
        brightness = dataEnvelope.brightness;
        productName = dataEnvelope.product_name;
       /* try {
            lastSeen = dateFormat.parse(dataEnvelope.last_seen);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        secondsSinceLastSeen = dataEnvelope.seconds_since_last_seen;

        hue = dataEnvelope.color.hue;
        saturation = dataEnvelope.color.saturation;
        kelvin = dataEnvelope.color.kelvin;

        hasColour = dataEnvelope.capabilities.has_color;
        hasVariableColourTemp = dataEnvelope.capabilities.has_variable_color_temp;
    }

    /**
     * @return if this light is enabled or not
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     *
     * @return the name of this light
     */
    public String getName() {
        return name;
    }

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
        return (int)hue;
    }

    public int getSaturation() {
        return (int)saturation*100;
    }

    public int getKelvin() {
        return kelvin;
    }

    public int getBrightness() {
        return (int) (brightness*100);
    }

    public ModelConstants.Power getPower() {
        return power;
    }

    public String getProductName() {
        return productName;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public double getSecondsSinceLastSeen() {
        return secondsSinceLastSeen;
    }

    public boolean hasColour() {
        return hasColour;
    }

    public boolean hasVariableColourTemp() {
        return hasVariableColourTemp;
    }

    @Override
    public String toString() {
        return "Light{" +
                "name='" + name + '\'' +
                ", connected=" + connected +
                ", hue=" + hue +
                ", saturation=" + saturation +
                ", kelvin=" + kelvin +
                ", brightness=" + brightness +
                ", power=" + power +
                ", productName='" + productName + '\'' +
                ", lastSeen=" + lastSeen +
                ", secondsSinceLastSeen=" + secondsSinceLastSeen +
                ", hasColour=" + hasColour +
                ", hasVariableColourTemp=" + hasVariableColourTemp +
                ", enabled=" + enabled +
             //   ", dateFormat=" + dateFormat +
                '}';
    }
}