package com.giganticsheep.wifilight.ui.control;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.LightSelector;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.util.ErrorSubscriber;

import org.jetbrains.annotations.Nullable;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/08/15. <p>
 * (*_*)
 */
abstract class OnListItemClickListener {

    protected final EventBus eventBus;
    protected final LightNetworkPresenter presenter;
    private final LightControlActivity activity;

    public OnListItemClickListener(@NonNull final EventBus eventBus,
                                   @NonNull final LightNetworkPresenter presenter,
                                   @NonNull final LightControlActivity activity) {
        this.eventBus = eventBus;
        this.presenter = presenter;
        this.activity = activity;
    }

    protected void closeDrawer() {
        activity.closeDrawer();
    }

    protected void selectorChanged(@NonNull final LightSelector.SelectorType type,
                                   @Nullable final String id) {
        eventBus.postMessage(new LightControl.LightSelectorChangedEvent(new LightSelector(type, id)))
                .subscribe(new ErrorSubscriber<>());
    }
}
