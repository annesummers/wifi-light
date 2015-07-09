package com.giganticsheep.wifilight.dagger;

import com.giganticsheep.wifilight.WifiLightApplication;

/**
 * Created by anne on 09/07/15.
 * (*_*)
 */
public interface WifiLightAppGraph extends WifiLightGraph {
    void inject(WifiLightApplication application);

    WifiLightApplication application();
}
