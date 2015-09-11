package com.giganticsheep.wifilight.ui.status.group;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.ui.base.GroupChangedEvent;
import com.giganticsheep.wifilight.ui.status.StatusPresenterBase;

import hugo.weaving.DebugLog;
import rx.Subscriber;
import timber.log.Timber;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 08/09/15. <p>
 * (*_*)
 */
public class GroupStatusPresenter extends StatusPresenterBase<GroupStatusView> {

    /**
     * Constructs the StatusPresenter object.  Injects itself into the supplied Injector.
     *
     * @param injector an Injector used to inject this object into a Component that will
     *                 provide the injected class members.
     */
    public GroupStatusPresenter(@NonNull final Injector injector) {
        injector.inject(this);
    }

    /**
     * Called with the details of a {@link com.giganticsheep.wifilight.api.model.Location} to display.
     *
     * @param event contains the new {@link com.giganticsheep.wifilight.api.model.Location}.
     */
    @DebugLog
    public void onEventMainThread(@NonNull final GroupChangedEvent event) {
        subscribe(lightControl.fetchGroup(event.getGroupId()),
                new Subscriber<Group>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("fetchGroup", e);
                    }

                    @Override
                    public void onNext(final Group group) {
                        if (isViewAttached()) {
                            getView().showGroup(group);
                        }
                    }
                });
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
         * @param presenter the class to inject.
         */
        void inject(final GroupStatusPresenter presenter);
    }
}
