package com.giganticsheep.wifilight.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.client.Response;
import rx.Subscriber;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class Light extends WifiLightObject{

    private final String mName;
    private boolean mConnected;
    private double mHue;
    private double mSaturation;
    private long mKelvin;
    private double mBrightness;

    private ModelConstants.Power mPower;

    private String mProductName;
    private Date mLastSeen;
    private double mSecondsSinceLastSeen;

    private boolean mHasColour;
    private boolean mHasVariableColourTemp;

    private boolean mEnabled;

    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-ddThh:mm:ssZ");

    /**
     * @param network the network this light is part of
     * @param name the name of this light
     */
    public Light(final LightNetwork network, final String id, final String name) {
        super(network, id);

        mName = name;
    }

    public Light(final LightNetwork network, boolean enabled, LightNetwork.LightDataResponse dataEnvelope) {
        super(network, dataEnvelope.id);

        mEnabled = enabled;

        mName = dataEnvelope.label;
        mConnected = dataEnvelope.connected;
        mPower = dataEnvelope.power.equals("on") ? ModelConstants.Power.ON : ModelConstants.Power.OFF;
        mBrightness = dataEnvelope.brightness;
        mProductName = dataEnvelope.product_name;
        try {
            mLastSeen = mDateFormat.parse(dataEnvelope.last_seen);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mSecondsSinceLastSeen = dataEnvelope.seconds_since_last_seen;

        mHue = dataEnvelope.color.hue;
        mSaturation = dataEnvelope.color.saturation;
        mKelvin = dataEnvelope.color.kelvin;

        mHasColour = dataEnvelope.capabilities.has_color;
        mHasVariableColourTemp = dataEnvelope.capabilities.has_variable_color_temp;
    }

    /**
     *
     * @return the name of this light
     */
    public final String name() {
        return mName;
    }

    /**
     * @return if this light is enabled or not
     */
    public final boolean enabled() {
        return mEnabled;
    }

    public final boolean connected() {
        return mConnected;
    }

    public final double hue() {
        return mHue;
    }

    /**
     * @param enabled is this light enabled
     */
    public final void setEnabled(final boolean enabled) {
        mEnabled = enabled;
    }

    /**
     * @param hue the hue to set this light
     */
    public final void setHue(final float hue) {
        mHue = hue;
    }

    /**
     * @param saturation the saturation to set this light
     */
    public final void setSaturation(final double saturation) {
        mSaturation = saturation;
    }

    /**
     * @param brightness the value (brightness) to set this light
     */
    public final void setBrightness(final double brightness) {
        mBrightness = brightness;
    }

    /**
     * @param kelvin the kelvin (warmth) to set this light
     */
    public final void setKelvin(final long kelvin) {
        mKelvin = kelvin;
    }

    public void toggle() {
        mPower = (mPower == ModelConstants.Power.ON ) ? ModelConstants.Power.OFF : ModelConstants.Power.ON;
    }

    public void setPower(ModelConstants.Power power) {
        mPower = power;
    }

    @Override
    public final String toString() {
        return "Light{" +
                " network=" + mNetwork +
                ", name=" + mName +
                ", enabled" + mEnabled +
                ", hue=" + mHue +
                ", saturation=" + mSaturation +
                ", brightness=" + mBrightness +
                ", kelvin=" + mKelvin +
                " }";
    }
}
