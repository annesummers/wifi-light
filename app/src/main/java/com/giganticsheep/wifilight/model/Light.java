package com.giganticsheep.wifilight.model;

import org.apache.http.impl.entity.EntityDeserializer;

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
    private long kelvin;
    private double brightness;

    private ModelConstants.Power power;

    private String productName;

    private Date lastSeen;
    private double secondsSinceLastSeen;

    private boolean hasColour;
    private boolean hasVariableColourTemp;

    private boolean enabled;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
    // TODO date

    /**
     * @param network the network this light is part of
     * @param name the name of this light
     */
    public Light(/*final LightNetwork network, */final String id, final String name) {
        super(/*network, */id);

        this.name = name;
    }

    public Light(/*final LightNetwork network, */boolean enabled, LightNetwork.LightDataResponse dataEnvelope) {
        super(/*network, */dataEnvelope.id);

        this.enabled = enabled;

        name = dataEnvelope.label;
        connected = dataEnvelope.connected;
        power = dataEnvelope.power.equals("on") ? ModelConstants.Power.ON : ModelConstants.Power.OFF;
        brightness = dataEnvelope.brightness;
        productName = dataEnvelope.product_name;
        try {
            lastSeen = dateFormat.parse(dataEnvelope.last_seen);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
    public double getHue() {
        return hue;
    }

    public double getSaturation() {
        return saturation;
    }

    public long getKelvin() {
        return kelvin;
    }

    public double getBrightness() {
        return brightness;
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

    /**
     * @param enabled is this light enabled
     */
   // public final void setEnabled(final boolean enabled) {
   //     this.enabled = enabled;
    //}

    /**
     * @param hue the hue to set this light
     */
   // public final void setHue(final float hue) {
   //     this.hue = hue;
   // }

    /**
     * @param saturation the saturation to set this light
     */
    //public final void setSaturation(final double saturation) {
    //    this.saturation = saturation;
   // }

    /**
     * @param brightness the value (brightness) to set this light
     */
  //  public final void setBrightness(final double brightness) {
  //      this.brightness = brightness;
  //  }

    /**
     * @param kelvin the kelvin (warmth) to set this light
     */
  //  public final void setKelvin(final long kelvin) {
   //     this.kelvin = kelvin;
  //  }

 //   public void toggle() {
  ////      power = (power == ModelConstants.Power.ON ) ? ModelConstants.Power.OFF : ModelConstants.Power.ON;

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
                ", dateFormat=" + dateFormat +
                '}';
    }
}
//
  //  public void setPower(ModelConstants.Power power) {
  //      this.power = power;
    //}

//}
