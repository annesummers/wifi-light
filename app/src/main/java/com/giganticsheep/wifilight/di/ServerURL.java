package com.giganticsheep.wifilight.di;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */

@Qualifier
@Retention(RUNTIME)
public @interface ServerURL {
}
