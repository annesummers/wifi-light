package com.giganticsheep.wifilight.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.ui.base.FragmentBase;
import com.giganticsheep.wifilight.mvp.presenter.LightEffectsPresenter;
import com.giganticsheep.wifilight.mvp.presenter.LightPresenterBase;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;

/**
 * Created by anne on 25/06/15.
 * (*_*)
 */
@FragmentArgsInherited
public class LightEffectsFragment extends LightFragmentBase {

    public static LightEffectsFragment newInstance(String name) {
        LightEffectsFragment fragment = new LightEffectsFragment();

        Bundle args = new Bundle();
        args.putString(FragmentBase.FRAGMENT_ARGS_NAME, name);
        fragment.setArguments(args);

        return fragment;
    }

    public LightEffectsFragment() {
        super();
    }

    @Override
    public LightPresenterBase createPresenter() {
        return new LightEffectsPresenter(getLightControlActivity().getComponent());
    }

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
    protected void showLight() { }

    @Override
    protected void enableViews(boolean enable) { }

    @Override
    protected void destroyViews() { }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getPresenter().onDestroy();
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
    }
}
