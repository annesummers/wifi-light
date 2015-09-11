package com.giganticsheep.wifilight.ui;

import android.content.SharedPreferences;

import com.giganticsheep.wifilight.WifiLightAppComponent;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.base.WifiLightTestBase;

import org.robolectric.RuntimeEnvironment;

import javax.inject.Inject;

/**
 * Created by anne on 10/07/15.
 * (*_*)
 */
public abstract class UITestBase extends WifiLightTestBase {

    @Inject protected SharedPreferences sharedPreferences;

    protected TestUIComponent component;
    protected WifiLightAppComponent appComponent;

    @Override
    protected void createComponentAndInjectDependencies() {
        appComponent = WifiLightAppComponent.Initializer.init((WifiLightApplication) RuntimeEnvironment.application);

        component = DaggerTestUIComponent.builder()
                .wifiLightAppComponent(appComponent)
                .build();

        component.inject(this);
    }
}
