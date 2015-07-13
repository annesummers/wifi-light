package com.giganticsheep.wifilight.ui;

import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.base.WifiLightTestBase;
import com.giganticsheep.wifilight.dagger.WifiLightAppComponent;
import com.giganticsheep.wifilight.ui.dagger.TestUIComponent;

import org.robolectric.RuntimeEnvironment;

/**
 * Created by anne on 10/07/15.
 * (*_*)
 */
public abstract class UITestBase extends WifiLightTestBase {
    private TestUIComponent component;

    @Override
    protected void createComponentAndInjectDependencies() {
        component = DaggerTestUIComponent.builder()
                .wifiLightAppComponent(WifiLightAppComponent.Initializer.init((WifiLightApplication) RuntimeEnvironment.application))
                .build();

        component.inject(this);
    }
}
