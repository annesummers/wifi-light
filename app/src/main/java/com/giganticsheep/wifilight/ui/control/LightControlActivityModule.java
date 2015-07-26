package com.giganticsheep.wifilight.ui.control;

import android.app.Activity;

import com.giganticsheep.wifilight.ui.base.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */

@Module
class LightControlActivityModule {

    private final Activity activity;

    LightControlActivityModule(Activity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    Activity provideActivity() {
        return activity;
    }
}
