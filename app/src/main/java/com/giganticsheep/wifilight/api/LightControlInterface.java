package com.giganticsheep.wifilight.api;

import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.StatusResponse;

import rx.Observable;

/**
 * Created by anne on 12/07/15.
 * (*_*)
 */
public interface LightControlInterface {

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
     * @param hue the hue to set the selected lights
     */
    Observable<StatusResponse> setHue(final int hue, float duration);

    /**
     * @param saturation the saturation to set the selected lights
     */
    Observable<StatusResponse> setSaturation(final int saturation, float duration);

    /**
     * @param brightness the brightness to set the selected lights
     */
    Observable<StatusResponse> setBrightness(final int brightness, float duration);

    /**
     * @param kelvin the kelvin (warmth to set the selected lights
     */
    Observable<StatusResponse> setKelvin(final int kelvin, float duration);

    /**
     * Toggles the power of the selected lights
     *
     */
    Observable<StatusResponse> togglePower();

    /**
     * @param power ON or OFF
     * @param duration how long to set the power change for
     */
    Observable<StatusResponse> setPower(final Power power, final float duration);

    Observable<Light> fetchLights(boolean fetchFromServer);

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
