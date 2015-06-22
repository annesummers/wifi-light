package com.giganticsheep.wifilight.model;

import com.giganticsheep.wifilight.WifiLightApplication;

import retrofit.client.Response;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class Light {

    private final String mName;
    private final LightNetwork mNetwork;

    private int mHue = 0;
    private int mSaturation = 100;
    private int mValue = 100;

    public Light(final LightNetwork network, final String name) {
        mNetwork = network;
        mName = name;
    }

    public final String name() {
        return mName;
    }

    public boolean enabled() {
        return true;
    }

    public void setHue(int hue) {
        mHue = hue;

        setHSVColour();
    }

    public void setSaturation(int saturation) {
        mSaturation = saturation;

        setHSVColour();
    }

    public void setValue(int value) {
        mValue = value;

        setHSVColour();
    }

    private void setHSVColour() {
        final String query = "hue:" +
                Integer.toString(mHue) +
                " saturation:" +
                Float.toString(((float) mSaturation) / 100) +
                " brightness:" +
                Float.toString(((float) mValue) / 100);
        mNetwork.setColour(query).subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        logError(e);
                    }

                    @Override
                    public void onNext(Response response) {
                        logDebug(response.toString());
                    }
                });
    }

    public final void logWarn(final String message) {
        WifiLightApplication.application().logWarn(toString() + " " + message);
    }

    public final void logError(final String message) {
        WifiLightApplication.application().logError(toString() + " ERROR : " + message);
    }

    public final void logError(final Throwable throwable) {
        WifiLightApplication.application().logError(toString() + " ERROR : " + throwable.getMessage());
    }

    public final void logInfo(final String message) {
        WifiLightApplication.application().logInfo(toString() + " " + message);
    }

    public final void logDebug(final String message) {
        WifiLightApplication.application().logDebug(toString() + " " + message);
    }

    @Override
    public String toString() {
        return "Light{" +
                "name=" + mName +
                "mHue=" + mHue +
                ", mSaturation=" + mSaturation +
                ", mValue=" + mValue +
                '}';
    }
}
