package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.api.network.NetworkDetails;
import com.giganticsheep.wifilight.dagger.HasComponent;
import com.giganticsheep.wifilight.dagger.WifiLightAppComponent;
import com.giganticsheep.wifilight.ui.base.BaseApplication;

import org.jetbrains.annotations.NonNls;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class WifiLightApplication extends BaseApplication implements HasComponent<WifiLightAppComponent> {

    @NonNls private static final String DEFAULT_SERVER_STRING = "https://api.lifx.com";
    @NonNls private static final String DEFAULT_URL_STRING1 = "v1beta1";
    @NonNls private static final String DEFAULT_URL_STRING2 = "lights";

    private WifiLightAppComponent component;

    @Override
    public final void onCreate() {
        super.onCreate();

        buildComponentAndInject();
    }

    @Override
    public WifiLightAppComponent getComponent() {
        return component;
    }

    public NetworkDetails getNetworkDetails() {
        return new NetworkDetails(
                getResources().getString(R.string.DEFAULT_API_KEY),
                DEFAULT_URL_STRING1,
                DEFAULT_URL_STRING2);
    }

    public String getServerURL() {
        return DEFAULT_SERVER_STRING;
    }

    private void buildComponentAndInject() {
        component = WifiLightAppComponent.Initializer.init(this);
        component.inject(this);
    }
}
