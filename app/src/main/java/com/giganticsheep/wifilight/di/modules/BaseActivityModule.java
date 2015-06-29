package com.giganticsheep.wifilight.di.modules;

import android.content.Context;

import com.giganticsheep.wifilight.di.PerActivity;
import com.giganticsheep.wifilight.ui.base.BaseActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */

/**
 * This module represents objects which exist only for the scope of a single activity. We can
 * safely create singletons using the activity instance because the entire object graph will only
 * ever exist inside of that activity.
 */
@Module
public class BaseActivityModule {
    private final BaseActivity activity;

    public BaseActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Context provideContext() {
        return activity;
    }

    @Provides
    @PerActivity
    BaseActivity provideActivity() {
        return activity;
    }
}