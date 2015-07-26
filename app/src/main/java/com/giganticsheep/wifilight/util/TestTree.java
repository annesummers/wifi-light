package com.giganticsheep.wifilight.util;

import timber.log.Timber;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 26/07/15. <p>
 * (*_*)
 */
public class TestTree extends Timber.Tree {
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        System.err.println(message);
    }
}
