package com.giganticsheep.nofragmentbase.application;

import android.app.Application;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/08/15. <p>
 * (*_*)
 */
public abstract class NoFragmentApplication extends Application {

    public static NoFragmentApplication instance;

    public void onCreate() {
        super.onCreate();

        instance = this;

        initialiseComponentAndInjectDependencies();
    }

    protected abstract void initialiseComponentAndInjectDependencies();
}
