package com.giganticsheep.wifilight.di;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;
import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */

@Qualifier
@Scope
@Retention(RUNTIME)
public @interface Network {
}