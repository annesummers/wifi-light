package com.giganticsheep.wifilight.base.dagger;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */

@Qualifier
@Retention(RUNTIME)
public @interface UIScheduler {
}