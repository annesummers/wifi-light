package com.giganticsheep.wifilight.ui;

import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.base.WifiLightTestBase;
import com.giganticsheep.wifilight.dagger.DaggerWifiLightAppComponent;
import com.giganticsheep.wifilight.dagger.WifiLightAppComponent;

import org.robolectric.RuntimeEnvironment;

/**
 * Created by anne on 10/07/15.
 * (*_*)
 */
public abstract class UITest extends WifiLightTestBase {
    protected TestUIComponent component;

    @Override
    protected void createComponentAndInjectDependencies() {
        component = DaggerTestUIComponent.builder()
                .wifiLightAppComponent(WifiLightAppComponent.Initializer.init((WifiLightApplication) RuntimeEnvironment.application))
                .build();

        component.inject(this);
    }
}
