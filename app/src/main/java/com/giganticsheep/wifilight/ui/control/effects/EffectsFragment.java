package com.giganticsheep.wifilight.ui.control.effects;

import android.support.v4.app.Fragment;

import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;

/**
 * Created by anne on 25/06/15.
 * (*_*)
 */
@FragmentArgsInherited
public class EffectsFragment extends Fragment {

    public EffectsFragment() {
        super();
    }
/*
    @NonNull
    @Override
    public LightPresenterBase createPresenter() {
        return new EffectsPresenter(getComponent());
    }

    @NonNull
    @Override
    public EffectsPresenter getPresenter() {
        return (EffectsPresenter) super.getPresenter();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_effects;
    }

    @Override
    protected void initialiseViews(View view) {  }

    @Override
    public void showLight(Light light) { }

    @Override
    protected void enableViews(boolean enable) { }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
    }*/
}
