package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.network.StatusResponse;
import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.Logger;
import com.giganticsheep.wifilight.mvp.view.LightView;
import com.giganticsheep.wifilight.util.Constants;
import com.giganticsheep.wifilight.util.ErrorSubscriber;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 *
 * Base class for all the Presenters that show information about a Light.
 */
public abstract class LightPresenterBase extends MvpBasePresenter<LightView> {

    @NonNull
    protected final Logger logger;

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Inject protected BaseLogger baseLogger;
    @Inject protected EventBus eventBus;
    @Inject protected LightControl lightControl;

    protected final SetLightSubscriber setLightSubscriber = new SetLightSubscriber();

    /**
     * Constructs the LightPresenterBase object.  Injects itself into the supplied Injector.
     *
     * @param injector an Injector used to inject this object into a Component that will
     *                 provide the injected class members.
     */
    protected LightPresenterBase(@NonNull Injector injector) {
        injector.inject(this);

        logger = new Logger(getClass().getName(), baseLogger);
    }

    protected void fetchLight(String id, @NonNull Subscriber<Light> subscriber) {
        subscribe(lightControl.fetchLight(id), subscriber);
    }

    protected void handleLightChanged(@NonNull Light light) {
        if (isViewAttached()) {
            getView().setLight(light);

            if(light.isConnected()) {
                if(light.getSecondsSinceLastSeen() > Constants.LAST_SEEN_TIMEOUT_SECONDS) {
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
     * Called when the Presenter is destroyed, overridden to cleanup members and
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
     * @param <T>
     */
    protected <T> void subscribe(@NonNull final Observable<T> observable, @NonNull Subscriber<T> subscriber) {
        compositeSubscription.add(observable.subscribe(subscriber));
    }

    /**
     * Subscribes to observable with ErrorSubscriber, retaining the resulting Subscription so
     * when the Presenter is destroyed the Observable can be unsubscribed from.
     *
     * @param observable the Observable to subscribe to
     * @param <T>
     */
    protected <T> void subscribe(@NonNull final Observable<T> observable) {
        subscribe(observable, new ErrorSubscriber<T>(logger));
    }

    public abstract void fetchLight(String id);

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightPresenterBase derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        void inject(LightPresenterBase lightPresenter);
    }

    private class SetLightSubscriber extends Subscriber<StatusResponse>  {

        @Override
        public void onCompleted() { }

        @Override
        public void onError(Throwable e) {
            if (isViewAttached()) {
                getView().showError(e);
            }
        }

        @Override
        public void onNext(StatusResponse light) { }
    }
}
