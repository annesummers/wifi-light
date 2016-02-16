package com.giganticsheep.nofragmentbase.ui.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by anne on 15/02/16.
 */
public class DumbFrameLayoutContainer extends FrameLayout
                                    implements ViewContainer {

    private final ContainerDelegate containerDelegate;

    public DumbFrameLayoutContainer(Context context) {
        super(context);

        containerDelegate = new ContainerDelegate(this);
    }

    public DumbFrameLayoutContainer(Context context, AttributeSet attrs) {
        super(context, attrs);

        containerDelegate = new ContainerDelegate(this);
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
    public void setScreen(Screen screen) {
        containerDelegate.setScreen(screen);
    }

    @Override
    public ScreenGroup getScreenGroup() {
        return containerDelegate.getScreenGroup();
    }

    @Override
    public boolean isOnScreen() {
        return containerDelegate.isOnScreen();
    }
}
