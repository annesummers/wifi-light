package com.giganticsheep.wifilight.api;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.network.StatusResponse;

import rx.Observable;

/**
 * Created by anne on 12/07/15.
 * (*_*)
 */
public interface LightControl {

    enum Power {
        ON ("on"),
        OFF ("off");

        private final String mName;

        Power(String name) {
            mName = name;
        }

        public String powerString() {
        return mName;
    }
    }

    /**
     * Sets the hue of the selected lights.
     *
     * @param hue the hue to set; an int between 0 and 360.
     * @param duration the duration to set the hue for.
     * @return the Observable to subscribe to.
     */
    @NonNull
    Observable<StatusResponse> setHue(final int hue, float duration);

    /**
     * Sets the saturation of the selected lights.
     *
     * @param saturation the saturation to set; an int between 0 and 100.
     * @param duration the duration to set the saturation for.
     * @return the Observable to subscribe to.
     */
    @NonNull
    Observable<StatusResponse> setSaturation(final int saturation, float duration);

    /**
     * Sets the brightness of the selected lights.
     *
     * @param brightness the brightness to set; an int between 0 and 100.
     * @param duration the duration to set the brightness for.
     * @return the Observable to subscribe to.
     */
    @NonNull
    Observable<StatusResponse> setBrightness(final int brightness, float duration);

    /**
     * Sets the kelvin (warmth) of the selected lights.
     *
     * @param kelvin the kelvin to set; an int between 2500 and 9000.
     * @param duration the duration to set the kelvin for.
     * @return the Observable to subscribe to.
     */
    @NonNull
    Observable<StatusResponse> setKelvin(final int kelvin, float duration);

    /**
     * Toggles the power of the selected lights
     *
     * @return the Observable to subscribe to.
     */
    @NonNull
    Observable<StatusResponse> togglePower();

    /**
     * Sets the power of the selected lights.
     *
     * @param power ON or OFF.
     * @param duration how long to set the power change for.
     * @return the Observable to subscribe to.
     */
    @NonNull
    Observable<StatusResponse> setPower(final Power power, final float duration);

    /**
     * Fetch all the Lights from the network.
     *
     * @param fetchFromServer whether to fetch the information from the server or to use the
     *                        cached information if we have it.
     * @return the Observable to subscribe to.
     */
    @NonNull
    Observable<Light> fetchLights(boolean fetchFromServer);

    /**
     * Fetches the light with the specified id.
     *
     * @param id a String representing the id of the Light to fetch.
     * @return the Observable to subscribe to.
     */
    @NonNull
    Observable<Light> fetchLight(final String id);

    class FetchLightsSuccessEvent { }

    class LightDetailsEvent {
        private final Light light;

        public LightDetailsEvent(Light light) {
            this.light = light;
        }

        public final Light light() {
            return light;
        }
    }
}
