package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightStatus;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.mvp.view.LightView;
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
    @Inject protected LightControl lightControl;

    /**
     * Constructs the LightPresenterBase object.  Injects itself into the supplied Injector.
     *
     * @param injector an Injector used to inject this object into a Component that will
     *                 provide the injected class members.
     */
    protected LightPresenterBase(@NonNull final Injector injector) {
        injector.inject(this);
    }

    /**
     * Fetches the Light with the given id.  Subscribes to the model's method using
     * the Subscriber given.
     *
     * @param id the id of the Light to fetch.
     * @param subscriber the Subscriber to subscribe with.
     */
    protected void fetchLight(@NonNull final String id,
                              @NonNull final Subscriber<Light> subscriber) {
        subscribe(lightControl.fetchLight(id), subscriber);
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
                    getView().showConnecting();
                } else {
                    getView().showConnected();
                }
            } else {
                getView().showDisconnected();
            }
        }
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
    protected <T> void subscribe(@NonNull final Observable<T> observable) {
        subscribe(observable, new ErrorSubscriber<T>());
    }

    /**
     * Fetches the {@link com.giganticsheep.wifilight.api.model.Light} with the given id.
     *
     * @param id the id of the Light to fetch.
     */
    public abstract void fetchLight(String id);

    public abstract Light getLight();

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

    public class SetLightSubscriber extends Subscriber<LightStatus>  {

        @Override
        public void onCompleted() { }

        @Override
        public void onError(Throwable e) {
            if (isViewAttached()) {
                getView().showError(e);
            }
        }

        @Override
        public void onNext(@NonNull final LightStatus light) {
            Light currentLight = getLight();

           if(light.id().equals(currentLight.id())) {
               if(isViewAttached()) {
                   if(light.getStatus() == LightControl.Status.OK) {
                       getView().showConnected();
                   } else if(light.getStatus() == LightControl.Status.OFF) {
                       getView().showDisconnected();
                   }
               }

               if(currentLight.isConnected() && light.getStatus() != LightControl.Status.OK ||
                  !currentLight.isConnected() && light.getStatus() != LightControl.Status.OFF) {

                   fetchLight(light.id());
               }
           }
        }
    }
}
