package com.giganticsheep.wifilight.ui.control;

import com.giganticsheep.wifilight.base.MockedTestBase;
import com.giganticsheep.wifilight.ui.WifiLightTestsComponent;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 28/08/15. <p>
 * (*_*)
 */
public class PresenterTestBase extends MockedTestBase {

    protected TestPresenterComponent component;

    @Override
    protected void createComponentAndInjectDependencies() {
        component = DaggerTestPresenterComponent.builder()
                .wifiLightTestsComponent(WifiLightTestsComponent.Initializer.init())
                .build();

        component.inject(this);
    }
}
