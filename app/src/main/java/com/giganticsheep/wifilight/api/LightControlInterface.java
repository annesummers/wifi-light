package com.giganticsheep.wifilight.api;

import com.giganticsheep.wifilight.api.network.LightDataResponse;

import java.util.List;

import rx.Observable;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
public interface LightControlInterface {
    Observable setHue(int hue, float duration);
    Observable setSaturation(int saturation, float duration);
    Observable setBrightness(int brightness, float duration);
    Observable setKelvin(int kelvin, float duration);

    Observable togglePower();
    Observable setPower(ModelConstants.Power power, float duration);

    Observable<LightDataResponse> fetchLights();
}
