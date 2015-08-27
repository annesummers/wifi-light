package com.giganticsheep.wifilight.ui.base.light;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightStatus;
import com.giganticsheep.wifilight.api.network.ServerErrorEventSubscriber;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.ErrorEvent;
import com.giganticsheep.wifilight.ui.base.LightChangedEvent;
import com.giganticsheep.wifilight.util.Constants;
import com.giganticsheep.wifilight.util.ErrorSubscriber;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Base class for all the Presenters that show information about a Light.<p>
 *
 * Created by anne on 29/06/15.<p>
 *
 * (*_*)
 */
public abstract class LightPresenterBase extends MvpBasePresenter<LightView> {

    @NonNull
    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Inject protected EventBus eventBus;
    @Inject public LightControl lightControl;

    /**
     * Constructs the LightPresenterBase object.  Injects itself into the supplied Injector.
     *
     * @param injector an Injector used to inject this object into a Component that will
     *                 provide the injected class members.
     */
    protected LightPresenterBase(@NonNull final Injector injector) {
        injector.inject(this);
    }

    @Override
    public void attachView(LightView view) {
        super.attachView(view);

        eventBus.registerForEvents(this).subscribe(new ErrorSubscriber<>());
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);

        eventBus.unregisterForEvents(this).subscribe(new ErrorSubscriber<>());
    }

    /**
     * Fetches the Light with the given getId.  Subscribes to the model's method using
     * the Subscriber given.
     *
     * @param id the id of the Light to fetch.
     */
    @DebugLog
    public void fetchLight(final String id) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        subscribe(lightControl.fetchLight(id), new Subscriber<Light>() {

            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) {
                subscribe(eventBus.postMessage(new ErrorEvent(e)));
            }

            @Override
            public void onNext(Light light) {
                subscribe(eventBus.postMessage(new LightChangedEvent(light)));
            }
        });
    }

    /**
     * Called to provide common functionality for when the Light has changed.  Sets the Light
     * in the associated view and calls to the view to show the correct screen depending on the
     * status of the Light.
     *
     * @param light the new Light.
     */
    @DebugLog
    public void handleLightChanged(@NonNull final Light light) {
        if (isViewAttached()) {
            if (light.isConnected()) {
                if (light.getSecondsSinceLastSeen() > Constants.LAST_SEEN_TIMEOUT_SECONDS) {
                    getView().showConnecting(light);
                } else {
                    getView().showConnected(light);
                }
            } else {
                getView().showDisconnected(light);
            }
        }
    }

    @DebugLog
    public void handleError(Throwable error) {
        if (isViewAttached()) {
            getView().showError(error);
        }
    }

    /**
     * Called with the details of a {@link com.giganticsheep.wifilight.api.model.Light} to display.
     *
     * @param event contains the new {@link com.giganticsheep.wifilight.api.model.Light}.
     */
    public void onEvent(@NonNull LightChangedEvent event) {
        handleLightChanged(event.getLight());
    }

    @DebugLog
    public synchronized void onEvent(@NonNull ErrorEvent event) {
        getView().showError(event.getError());
    }

    /**
     * Called when the Presenter is destroyed; overridden to cleanup members and
     * to unsubscribe from any services or events the Presenter may be subscribed to
     */
    public void onDestroy() {
        compositeSubscription.unsubscribe();
    }

    /**
     * Subscribes to observable with subscriber, retaining the resulting Subscription so
     * when the Presenter is destroyed the Observable can be unsubscribed from.
     *
     * @param observable the Observable to subscribe to
     * @param subscriber the Subscriber to subscribe with
     * @param <T> the type the Observable is observing
     */
    protected <T> void subscribe(@NonNull final Observable<T> observable,
                                 @NonNull final Subscriber<T> subscriber) {
        compositeSubscription.add(observable.subscribe(subscriber));
    }

    /**
     * Subscribes to observable with ErrorSubscriber, retaining the resulting Subscription so
     * when the Presenter is destroyed the Observable can be unsubscribed from.
     *
     * @param observable the Observable to subscribe to
     * @param <T> the type the Observable is observing
     */
    private <T> void subscribe(@NonNull final Observable<T> observable) {
        subscribe(observable, new ErrorSubscriber<T>());
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
         * @param lightPresenter the class to inject.
         */
        void inject(final LightPresenterBase lightPresenter);
    }

    public class SetLightSubscriber extends ServerErrorEventSubscriber<LightStatus> {

        public SetLightSubscriber() {
            super(eventBus);
        }

        @Override
        public void onNext(@NonNull final LightStatus light) {
            fetchLight(light.getId());
        }
    }
}
