package com.giganticsheep.wifilight.ui.navigation.group;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.base.error.ErrorEvent;
import com.giganticsheep.wifilight.ui.base.GroupChangedEvent;
import com.giganticsheep.wifilight.ui.base.PresenterBase;

import hugo.weaving.DebugLog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/09/15. <p>
 * (*_*)
 */
public class GroupPresenter extends PresenterBase<GroupView> {

    public GroupPresenter(@NonNull final Injector injector) {
        injector.inject(this);
    }

   /* @DebugLog
    final public void fetchLightNetwork() {
        if (isViewAttached()) {
            getView().showLoading();
        }

        subscribe(lightControl.fetchLightNetwork());
    }*/

    /**
     * Fetches the Group with the given id.  Subscribes to the model's method using
     * the Subscriber given.
     *
     * @param groupId the id of the v to fetch.
     */
    @DebugLog
    public void fetchGroup(final String groupId) {
        subscribe(lightControl.fetchGroup(groupId)
                .observeOn(AndroidSchedulers.mainThread()),
                new Subscriber<Group>() {

            @Override
            public void onCompleted() { }

            @Override
            public void onError(@NonNull final Throwable e) {
                eventBus.postMessage(new ErrorEvent(e));
            }

            @Override
            public void onNext(@NonNull final Group group) {
                //subscribe(eventBus.postMessage(new GroupChangedEvent(group)));
                getView().showGroup(group);
            }
        });
    }

    /**
     * Called with the details of a {@link com.giganticsheep.wifilight.api.model.Location} to display.
     *
     * @param event contains the new {@link com.giganticsheep.wifilight.api.model.Location}.
     */
    @DebugLog
    public void onEventBackgroundThread(@NonNull final GroupChangedEvent event) {
        fetchGroup(event.getGroupId());
    }

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightPresenterBase derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        /**
         * Injects the lightPresenter class into the Component implementing this interface.
         *
         * @param groupPresenter the class to inject.
         */
        void inject(final GroupPresenter groupPresenter);
    }
}
