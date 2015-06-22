package com.giganticsheep.wifilight.model;

import com.giganticsheep.wifilight.Logger;

import org.jetbrains.annotations.NonNls;

import retrofit.client.Response;
import rx.Subscriber;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class Light {

    @NonNls private static final String LABEL_HUE = "hue:";
    @NonNls private static final String LABEL_SATURATION = "saturation:";
    @NonNls private static final String LABEL_VALUE = "value:";

    @NonNls private static final char SPACE = ' ';

    @SuppressWarnings("FieldNotUsedInToString")
    protected final Logger mLogger = new Logger(getClass().getName());

    private final String mName;
    private final LightNetwork mNetwork;

    private int mHue = 0;
    private float mSaturation = 1.0F;
    private float mValue = 1.0F;

    private boolean mEnabled;

    /**
     * @param network the network this light is part of
     * @param name the name of this light
     */
    public Light(final LightNetwork network, final String name) {
        mNetwork = network;
        mName = name;
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

    /**
     * @param enabled is this light enabled
     */
    public final void setEnabled(final boolean enabled) {
        mEnabled = enabled;
    }

    /**
     * @param hue the hue to set this light
     */
    public final void setHue(final int hue) {
        mHue = hue;

        setHSVColour();
    }

    /**
     * @param saturation the saturation to set this light
     */
    public final void setSaturation(final float saturation) {
        mSaturation = saturation;

        setHSVColour();
    }

    /**
     * @param value the value (brightness) to set this light
     */
    public final void setValue(final float value) {
        mValue = value;

        setHSVColour();
    }

    private void setHSVColour() {
        final String query = LABEL_HUE + Integer.toString(mHue) + SPACE +
                LABEL_SATURATION + Float.toString(mSaturation) + SPACE +
                LABEL_VALUE + Float.toString(mValue);

        mNetwork.setColour(query).subscribe(new Subscriber<Response>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(final Throwable e) {
                mLogger.error(e);
            }

            @Override
            public void onNext(final Response t) {
                mLogger.debug(t.toString());
            }
        });
    }

    @Override
    public final String toString() {
        return "Light{" +
                " network=" + mNetwork +
                ", name=" + mName +
                ", enabled" + mEnabled +
                ", hue=" + mHue +
                ", saturation=" + mSaturation +
                ", value=" + mValue +
                " }";
    }
}
