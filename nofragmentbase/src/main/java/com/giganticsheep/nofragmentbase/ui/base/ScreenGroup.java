package com.giganticsheep.nofragmentbase.ui.base;

import android.os.Parcelable;

import de.greenrobot.event.EventBus;

/**
 * Created by anne on 11/11/15.
 */
public abstract class ScreenGroup implements Parcelable {

    private final EventBus eventBus;

    public ScreenGroup() {
        eventBus = new EventBus();
    }

    public void postControlEvent(Object event) {
        eventBus.post(event);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void registerForEvents(Screen screen) {
        eventBus.register(screen);
    }

    public void unRegisterForEvents(Screen screen) {
        eventBus.unregister(screen);
    }

    public void registerForEvents(FlowActivity activity) {
        eventBus.register(activity);
    }

    public void unRegisterForEvents(FlowActivity activity) {
        eventBus.unregister(activity);
    }
}
