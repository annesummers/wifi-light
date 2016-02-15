package com.giganticsheep.nofragmentbase.ui.base;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by anne on 03/11/15.
 */
public abstract class Screen<P extends PresenterBase> implements Parcelable {

    private ScreenGroup screenGroup;

    private int inAnimation = android.R.animator.fade_in;
    private int outAnimation = android.R.animator.fade_out;

    protected Screen(ScreenGroup screenGroup) {
        this.screenGroup = screenGroup;

        injectDependencies();
    }

    protected Screen(Parcel in) {
        screenGroup = in.readParcelable(ScreenGroup.class.getClassLoader());
        inAnimation = in.readInt();
        outAnimation = in.readInt();
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

    public abstract P getPresenter();

    protected abstract void injectDependencies();

    protected abstract int layoutId();

    public interface ViewInterfaceBase { }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(screenGroup, flags);
        dest.writeInt(inAnimation);
        dest.writeInt(outAnimation);
    }
}
