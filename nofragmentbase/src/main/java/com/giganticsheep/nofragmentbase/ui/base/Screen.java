package com.giganticsheep.nofragmentbase.ui.base;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import de.greenrobot.event.NoSubscriberEvent;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by anne on 03/11/15.
 */
public abstract class Screen<V extends Screen.ViewActionBase, G extends ScreenGroup>
        implements Parcelable, SubscriptionHandler {

    private ScreenGroup screenGroup;
    private ViewStateHandler viewState;

    private int inAnimation = android.R.animator.fade_in;
    private int outAnimation = android.R.animator.fade_out;

    private final SubscriptionDelegate subscriptionDelegate = new SubscriptionDelegate();

    public V getView() {
        return view;
    }

    private V view;
    boolean hasData = false;

    protected Screen(ScreenGroup screenGroup) {
        this.screenGroup = screenGroup;
        this.screenGroup.registerForEvents(this);

        this.viewState = createViewState();

        injectDependencies();
    }

    protected Screen(Parcel in) {
        this.screenGroup = in.readParcelable(ScreenGroup.class.getClassLoader());
        this.screenGroup.registerForEvents(this);

        inAnimation = in.readInt();
        outAnimation = in.readInt();
        viewState = in.readParcelable(ViewStateHandler.class.getClassLoader());

        injectDependencies();
    }

    public View inflateView(Context context, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(layoutId(), parent, false);

        ((ViewContainer)view).setScreen(this);

        return view;
    }

    public ScreenGroup getScreenGroup() {
        return screenGroup;
    }

    protected void setInAnimation(int inAnimation) {
        this.inAnimation = inAnimation;
    }

    protected void setOutAnimation(int outAnimation) {
        this.outAnimation = outAnimation;
    }

    public int getInAnimation() {
        return inAnimation;
    }

    public int getOutAnimation() {
        return outAnimation;
    }

    public ViewState getViewState() {
        return viewState;
    }

    public void hide() {
        screenGroup.unRegisterForEvents(this);
        clearSubscriptions();
    }

    protected ViewStateHandler createViewState() {
        return new ViewStateHandler();
    }

    @Override
    public <T> Subscription subscribe(@NonNull final Observable<T> observable,
                                      @NonNull final Subscriber<T> subscriber) {
        return subscriptionDelegate.subscribe(observable, subscriber);
    }

    @Override
    public void shutdown() {
        subscriptionDelegate.shutdown();
    }

    @Override
    public void clearSubscriptions() {
        subscriptionDelegate.clearSubscriptions();
    }

    // View handling

    void attachView(V view) {
        this.view = view;

        apply();
    }

    void detachView(V view) {
        this.view = null;
    }

    protected void setHasData() {
        hasData = true;

        apply();
    }

    void attachView(ViewState viewState, V view) {
        attachView(view);
        this.viewState.attachView(viewState);
    }

    void detachView(ViewState viewState, V view) {
        detachView(view);
        this.viewState.detachView(viewState);
    }

    private void apply() {
        if(hasData && view != null) {
            doAction();
        }
    }

    // Parcelling

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(screenGroup, flags);
        dest.writeInt(inAnimation);
        dest.writeInt(outAnimation);
        dest.writeParcelable(viewState, flags);
    }

    // Event handling

    public void onEvent(NoSubscriberEvent event) { }

    public void onEventMainThread(FlowActivity.DismissErrorDialogEvent unused) {
        getViewState().setStateShowing();

       /* if (waitingToShow) {
            if (isOnScreen() && isAttached) {
                waitingToShow = false;
                doAction();
            }
        }*/
    }

    protected abstract void injectDependencies();

    protected abstract int layoutId();

    protected abstract void doAction();

    public interface ViewActionBase { }

    protected static abstract class ScreenSubscriber<T> extends Subscriber<T> {

        protected final WeakReference<Screen> screenWeakReference;

        public ScreenSubscriber(Screen screen) {
            screenWeakReference = new WeakReference<>(screen);
        }
    }
}