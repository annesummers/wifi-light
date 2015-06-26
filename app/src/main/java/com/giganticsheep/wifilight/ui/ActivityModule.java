package com.giganticsheep.wifilight.ui;

import android.content.Context;

import com.giganticsheep.wifilight.api.AndroidModule;
import com.giganticsheep.wifilight.api.LightControlInterface;
import com.giganticsheep.wifilight.ui.rx.ForActivity;
import com.giganticsheep.wifilight.ui.rx.RXActivity;

import javax.inject.Singleton;

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
@Module(
        injects = {
                MainActivity.class,
                LightColourFragment.class,
                LightEffectsFragment.class,
                LightDetailsFragment.class
        },
        addsTo = AndroidModule.class,
        library = true
)

public class ActivityModule {
    private final RXActivity activity;

    public ActivityModule(RXActivity activity) {
        this.activity = activity;
    }

    /**
     * Allow the activity context to be injected but require that it be annotated with
     * {@link ForActivity @ForActivity} to explicitly differentiate it from application context.
     */
    @Provides
    @Singleton
    @ForActivity
    Context provideActivityContext() {
        return activity;
    }

    @Provides
    @Singleton
    LightControlInterface providesLightNetworkController() {
        return new MainActivity.LightNetworkController((MainActivity) activity);
    }
}