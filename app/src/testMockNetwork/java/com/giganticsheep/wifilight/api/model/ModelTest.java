package com.giganticsheep.wifilight.api.model;

import com.giganticsheep.wifilight.WifiLightTestsComponent;
import com.giganticsheep.wifilight.base.MockedTestBase;

/**
 * Created by anne on 10/07/15.
 * (*_*)
 */
abstract class ModelTest extends MockedTestBase {
    private TestModelComponent component;

    @Override
    protected void createComponentAndInjectDependencies() {
        component = DaggerTestModelComponent.builder()
                .wifiLightTestsComponent(WifiLightTestsComponent.Initializer.init())
                .build();

        component.inject(this);
    }
}
