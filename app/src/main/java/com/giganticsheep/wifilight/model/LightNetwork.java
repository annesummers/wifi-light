package com.giganticsheep.wifilight.model;

import com.giganticsheep.wifilight.WifiLightApplication;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.client.Response;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class LightNetwork {

    static final RestAdapter mRestAdapter;
    static final LightService mLightService;

    private static final String LIGHTS = "/lights";
    private static final String ALL = "/all";

    static {
        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint(WifiLightApplication.application().serverURL())
                .build();

        mLightService = mRestAdapter.create(LightService.class);
    }

    private final List<Light> mLights = new ArrayList<>();

    public LightNetwork(String aPIKey) {
        findLights();
        mLights.add(new Light(this, "Nyan"));
    }

    private void findLights() {
        service().listLights(baseUrl(), authorisation());
    }

    public void setHue(int hue) {
        for(Light light : mLights) {
            if(light.enabled()) {
                light.setHue(hue);
            }

        }
    }

    public void setSaturation(int saturation) {
        for(Light light : mLights) {
            if(light.enabled()) {
                light.setSaturation(saturation);
            }

        }
    }

    public void setValue(int value) {
        for(Light light : mLights) {
            if(light.enabled()) {
                light.setValue(value);
            }

        }
    }

    @Override
    public String toString() {
        return "LightNetwork{" +
                "mLights=" + mLights +
                '}';
    }

    public String authorisation() {
        return "Bearer " + WifiLightApplication.application().aPIKey();
    }

    public LightService service() {
        return mLightService;
    }

    public String baseUrl() {
        return WifiLightApplication.application().baseUrl() + ALL;
    }

    public Observable<Response> setColour(String query) {
        return service().setColour(baseUrl() + "/color", authorisation(), query)
                .subscribeOn(Schedulers.io());
    }
}
