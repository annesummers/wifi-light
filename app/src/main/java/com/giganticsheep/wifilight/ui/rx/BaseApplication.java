package com.giganticsheep.wifilight.ui.rx;

import android.app.Application;

import com.giganticsheep.wifilight.di.components.BaseApplicationComponent;
import com.squareup.otto.Bus;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by anne on 22/06/15.
 */
public abstract class BaseApplication extends Application {
    private FragmentFactory fragmentFactory;

    private final BaseLogger baseLogger = new BaseLogger(this);
    private final EventBus eventBus = new EventBus();

    private BaseApplicationComponent baseApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        fragmentFactory = createFragmentFactory();

        baseApplicationComponent = createApplicationComponent();
    }

    public BaseApplicationComponent getApplicationComponent() {
        return baseApplicationComponent;
    }

    public FragmentFactory getFragmentFactory() {
        return fragmentFactory;
    }

   // public BaseLogger getBaseLogger() {
   //     return baseLogger;
   // }

    public EventBus getEventBus() {
        return eventBus;
    }

    protected abstract FragmentFactory createFragmentFactory();

    protected abstract BaseApplicationComponent createApplicationComponent();

  //  public LoggerComponent getLoggerComponent() {
   //     return loggerComponent;
   // }

    public class EventBus {

        private final Bus bus = new Bus();
        /**
         * Posts a message to the global message bus.  Classes must register to receive messages
         * and much subscribe to  a specific message to receive it
         *
         * @param messageObject the object to post to the bus
         */
        public Observable postMessage(final Object messageObject) {
            return Observable.create(new Observable.OnSubscribe<Object>() {
                @Override
                public void call(Subscriber<? super Object> subscriber) {
                    try {
                        bus.post(messageObject);
                    } catch(Exception e) {
                        subscriber.onError(e);
                    }

                    subscriber.onNext(messageObject);
                    subscriber.onCompleted();
                }
            }).subscribeOn(AndroidSchedulers.mainThread());
        }

        public void registerForEvents(Object myClass) {
            bus.register(myClass);
        }

        public void unregisterForEvents(Object myClass) {
            bus.unregister(myClass);
        }
    }

    public interface FragmentFactory {
        Observable<? extends BaseFragment> createFragmentAsync(String name);
        BaseFragment createFragment(String name) throws Exception;
    }

    //public abstract Observable<? extends RXFragment> createFragmentAsync(String name);
   // public abstract RXFragment createFragment(String name) throws Exception;
}
