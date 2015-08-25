package com.giganticsheep.wifilight.ui.control.network;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.LightSelector;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.ui.control.LightControlActivity;
import com.giganticsheep.wifilight.util.ErrorSubscriber;

import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/08/15. <p>
 * (*_*)
 */
abstract class OnNetworkItemClickListener {

    @Inject EventBus eventBus;
    @Inject LightNetworkPresenter presenter;
    @Inject LightNetworkDrawerFragment fragment;

    protected OnNetworkItemClickListener(@NonNull final Injector injector) {
        injector.inject(this);
    }

    protected void closeDrawer() {
        eventBus.postMessage(new LightControlActivity.CloseDrawerEvent())
                .subscribe(new ErrorSubscriber<>());
    }

    protected void selectorChanged(@NonNull final LightSelector.SelectorType type,
                                   @Nullable final String id) {
        eventBus.postMessage(new LightControl.LightSelectorChangedEvent(new LightSelector(type, id)))
                .subscribe(new ErrorSubscriber<>());
    }

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightFragmentBase derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        void inject(OnNetworkItemClickListener onNetworkItemClickListener);
    }
}
