package com.giganticsheep.wifilight.ui.rx;

import android.app.Application;

import rx.Observable;

/**
 * Created by anne on 22/06/15.
 */
public abstract class RXApplication extends Application {

    public abstract Observable<? extends RXFragment> createFragmentAsync(String name);
    public abstract RXFragment createFragment(String name) throws Exception;
}
