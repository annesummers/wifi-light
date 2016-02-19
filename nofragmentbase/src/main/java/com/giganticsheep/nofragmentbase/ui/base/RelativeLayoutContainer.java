package com.giganticsheep.nofragmentbase.ui.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import flow.NotPersistent;

/**
 * Created by anne on 08/11/15.
 */
@NotPersistent
public abstract class RelativeLayoutContainer<S extends Screen> extends RelativeLayout
                                                implements ViewContainer<S>, ViewState {

    private final ContainerDelegate<S> containerDelegate;

    public RelativeLayoutContainer(Context context) {
        super(context);

        containerDelegate = new ContainerDelegate(this, getViewAction(), this);
    }

    public RelativeLayoutContainer(Context context, AttributeSet attrs) {
        super(context, attrs);

        containerDelegate = new ContainerDelegate(this, getViewAction(), this);
    }

    @Override
    protected final void onFinishInflate() {
        super.onFinishInflate();

        containerDelegate.bindView();
        setupViews();
    }

    // from ViewContainer

    @Override
    public float getXFraction() {
        return containerDelegate.getXFraction();
    }

    @Override
    public void setXFraction(float xFraction) {
        containerDelegate.setXFraction(xFraction);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        containerDelegate.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        containerDelegate.onDetachedFromWindow();
    }

    @Override
    public void setScreen(S screen) {
        containerDelegate.setScreen(screen);

        onCreated();
    }

    @Override
    public S getScreen() {
        return containerDelegate.getScreen();
    }

    @Override
    public ScreenGroup getScreenGroup() {
        return containerDelegate.getScreenGroup();
    }

    @Override
    public boolean isOnScreen() {
        return containerDelegate.isOnScreen();
    }

    // from ViewState

    @Override
    public void setStateError(String error) {
        hideLoading();
        showError(error);
    }

    @Override
    public void setStateError(Throwable throwable) {
        hideLoading();
        showError(throwable);
    }

    @Override
    public void setStateLoading() {
        showLoading();
    }

    @Override
    public void setStateShowing() {
        hideLoading();
        showData();
    }

    protected void showLoading() {
        getScreenGroup().postControlEvent(new FlowActivity.FullScreenLoadingEvent(false));
    }

    protected void hideLoading() {
        getScreenGroup().postControlEvent(new FlowActivity.FullScreenLoadingEvent(false));
    }

    private void showError(Throwable throwable) {
        getScreenGroup().postControlEvent(new FlowActivity.ShowErrorEvent(throwable));
    }

    private void showError(String error) {
        getScreenGroup().postControlEvent(new FlowActivity.ShowErrorEvent(error));
    }

    protected abstract Screen.ViewActionBase getViewAction();

    protected abstract void showData();

    protected abstract void setupViews();
    protected abstract void onCreated();
}
