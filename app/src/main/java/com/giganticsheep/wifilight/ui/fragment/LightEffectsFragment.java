package com.giganticsheep.wifilight.ui.fragment;

import android.support.annotation.NonNull;
import android.view.View;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.mvp.presenter.LightEffectsPresenter;
import com.giganticsheep.wifilight.mvp.presenter.LightPresenterBase;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;

/**
 * Created by anne on 25/06/15.
 * (*_*)
 */
@FragmentArgsInherited
public class LightEffectsFragment extends LightFragmentBase {

    public LightEffectsFragment() {
        super();
    }

    @NonNull
    @Override
    public LightPresenterBase createPresenter() {
        return new LightEffectsPresenter(getLightControlActivity().getComponent(),
                                         getLightControlActivity().getPresenter());
    }

    @NonNull
    @Override
    public LightEffectsPresenter getPresenter() {
        return (LightEffectsPresenter) super.getPresenter();
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
