package com.giganticsheep.wifilight.model;

import com.giganticsheep.wifilight.Logger;
import com.giganticsheep.wifilight.WifiLightApplication;

import org.jetbrains.annotations.NonNls;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.client.Response;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class LightNetwork {

    private static final RestAdapter mRestAdapter;
    private static final LightService mLightService;

    @NonNls private static final char SPACE = ' ';

    @NonNls private static final String URK_ALL = "/all";
    @NonNls private static final String URL_COLOR = "/color";
    @NonNls private static final String LABEL_BEARER = "Bearer";

    @SuppressWarnings("FieldNotUsedInToString")
    protected final Logger mLogger = new Logger(getClass().getName());

    static {
        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint(WifiLightApplication.application().serverURL())
                .build();

        mLightService = mRestAdapter.create(LightService.class);
    }

    private final Iterable<Light> mLights = new ArrayList<>();
    private final String mAPIKey;

    /**
     * @param aPIKey
     */
    public LightNetwork(final String aPIKey) {
        mAPIKey = aPIKey;

        findLights()
        .subscribe();
    }

    /**
     * @param hue the hue to set the enabled lights
     */
    public final void setHue(final int hue) {
        for(final Light light : mLights) {
            if(light.enabled()) {
                light.setHue(hue);
            }
        }
    }

    /**
     * @param saturation the saturation to set the enabled lights
     */
    public final void setSaturation(final int saturation) {
        for(final Light light : mLights) {
            if(light.enabled()) {
                light.setSaturation((float)saturation/100);
            }
        }
    }

    /**
     * @param value the value (brightness) to set the enabled lights
     */
    public final void setValue(final int value) {
        for(final Light light : mLights) {
            if(light.enabled()) {
                light.setValue((float)value/100);
            }
        }
    }

    /**
     * @param query the colour query
     * @return the Observable to subscribe to
     */
    final Observable<Response> setColour(final String query) {
        return service().setColour(baseUrl() + URL_COLOR, authorisation(), query)
                .subscribeOn(Schedulers.io());
    }

    private String authorisation() {
        return LABEL_BEARER + SPACE + mAPIKey;
    }

    private final LightService service() {
        return mLightService;
    }

    private final String baseUrl() {
        return WifiLightApplication.application().baseUrl() + URK_ALL;
    }

    private Observable<String> findLights() {
        return service().listLights(baseUrl(), authorisation())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(final Throwable throwable) {
                        mLogger.error(throwable);
                    }
                })
                .flatMap(new Func1<Response, Observable<String>>() {
                    @Override
                    public Observable<String> call(final Response response) {
                        mLogger.debug(response.toString());
                        return Observable.just(response.toString());
                    }
                });
    }

    @Override
    public String toString() {
        return "LightNetwork{" +
                "mLights=" + mLights +
                '}';
    }
}
