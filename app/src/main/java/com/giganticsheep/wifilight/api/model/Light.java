package com.giganticsheep.wifilight.api.model;

import com.giganticsheep.wifilight.api.LightControl;

import java.util.Date;

/**
 * Created by anne on 13/07/15.
 * (*_*)
 */
public interface Light extends WifiLightData  {

    /**
     * @return if this light is connected or not.
     */
    boolean isConnected();

    /**
     * @return the hue of this light.
     */
    int getHue();

    /**
     * @return the saturation of this light.
     */
    int getSaturation();

    /**
     * @return the kelvin of this light.
     */
    int getKelvin();

    /**
     * @return the brightness of this light.
     */
    int getBrightness();

    /**
     * The power status of this light.
     *
     * @return ON or OFF
     */
    LightControl.Power getPower();

    /**
     * @return when this light was last seen.
     */
    Date getLastSeen();

    /**
     * @return the seconds since this light was last seen.
     */
    double getSecondsSinceLastSeen();

    /**
     * @return the product name
     */
    String getProductName();

    /**
     * @return whether this light has colour or not
     */
    boolean hasColour();

    /**
     * @return whether this light has colour temperature or not
     */
    boolean hasVariableColourTemp(); // conversion methods
}
