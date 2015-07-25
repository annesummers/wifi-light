package com.giganticsheep.wifilight.ui.fragment;

import android.support.annotation.NonNull;
import android.view.View;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.mvp.presenter.EffectsPresenter;
import com.giganticsheep.wifilight.mvp.presenter.LightPresenterBase;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;

/**
 * Created by anne on 25/06/15.
 * (*_*)
 */
@FragmentArgsInherited
public class EffectsFragment extends LightFragmentBase {

    public EffectsFragment() {
        super();
    }

    @NonNull
    @Override
    public LightPresenterBase createPresenter() {
        return new EffectsPresenter(getLightControlActivity().getComponent());
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
    protected void showLight(Light light) { }

    @Override
    protected void enableViews(boolean enable) { }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
    }
}
