package com.giganticsheep.wifilight.api.model;

import com.giganticsheep.wifilight.WifiLightTestsComponent;
import com.giganticsheep.wifilight.base.WifiLightTestBase;

/**
 * Created by anne on 10/07/15.
 * (*_*)
 */
public class ModelTest extends WifiLightTestBase {
    private TestModelComponent component;

    @Override
    protected void createComponentAndInjectDependencies() {
        component = DaggerTestModelComponent.builder()
                .wifiLightTestsComponent(WifiLightTestsComponent.Initializer.init())
                .build();

        component.inject(this);
    }
}
