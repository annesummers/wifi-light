package com.giganticsheep.wifilight.base;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 28/07/15. <p>
 * (*_*)
 */
public abstract class MockedTestBase extends WifiLightTestBase {

    @Inject Timber.Tree loggerTree;

    public MockedTestBase() {
        super();

        Timber.plant(loggerTree);
    }
}
