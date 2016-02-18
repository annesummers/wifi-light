package com.giganticsheep.wifilight.ui.locations;

import android.support.annotation.NonNull;
import android.widget.BaseExpandableListAdapter;

import com.giganticsheep.wifilight.api.model.LightNetwork;

import javax.inject.Inject;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/08/15. <p>
 * (*_*)
 */
public abstract class LightNetworkAdapterBase extends BaseExpandableListAdapter {

    protected final Injector injector;

    @Inject LightNetworkDrawerFragment fragment;

    protected LightNetwork lightNetwork = new LightNetwork();

    //@DebugLog
    LightNetworkAdapterBase(@NonNull final Injector injector) {
        this.injector = injector;

        injector.inject(this);
    }

    void setLightNetwork(@NonNull final LightNetwork lightNetwork) {
        this.lightNetwork = lightNetwork;
    }

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling the DrawerAdapter to inject itself
     * into the Component.
     */
    public interface Injector {
        /**
         * Injects the DrawerAdapter class into the Component implementing this interface.
         *
         * @param adapter the class to inject.
         */
        void inject(final LightNetworkAdapterBase adapter);
    }
}
