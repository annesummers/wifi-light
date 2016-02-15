package com.giganticsheep.nofragmentbase.ui.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import flow.NotPersistent;

/**
 * Created by anne on 08/11/15.
 */
@NotPersistent
public abstract class RelativeLayoutContainer extends RelativeLayout
                                                implements ViewContainer,
                                                ViewStateInterface {

    private boolean isOnScreen = false;
    private Screen screen;

    public RelativeLayoutContainer(Context context) {
        super(context);
    }

    public RelativeLayoutContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected final void onFinishInflate() {
        super.onFinishInflate();

        bindView();
        setupViews();
        onCreated();
    }

    @Override
    public float getXFraction() {
        final int width = getWidth();
        if (width != 0) return getX() / getWidth();
        else return getX();
    }

    @Override
    public void setXFraction(float xFraction) {
        final int width = getWidth();
        setX((width > 0) ? (xFraction * width) : -9999);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        screen.getPresenter().addView(this, getViewInterface());

        isOnScreen = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        screen.getPresenter().removeView(this, getViewInterface());

        isOnScreen = false;
    }

    @Override
    public void setScreen(Screen screen) {
        this.screen = screen;
    }

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

    protected ScreenGroup getScreenGroup() {
        return screen.getScreenGroup();
    }

    protected void hideLoading() {
        EventBus.getDefault().post(new FlowActivity.FullScreenLoadingEvent(false));
    }

    protected boolean isOnScreen() {
        return isOnScreen;
    }

    private void bindView() {
        ButterKnife.bind(this, this);
    }

    private void showError(Throwable throwable) {

    }

    private void showError(String error) {

    }

    protected abstract Screen.ViewInterfaceBase getViewInterface();

    protected abstract void showLoading();

    protected abstract void showData();

    protected abstract void onCreated();

    protected abstract void setupViews();
}
