package com.giganticsheep.wifilight.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.ui.base.BaseFragment;
import com.giganticsheep.wifilight.mvp.presenter.LightDetailsPresenter;
import com.giganticsheep.wifilight.mvp.presenter.LightPresenter;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;

import butterknife.InjectView;

/**
 * Created by anne on 25/06/15.
 * (*_*)
 */

@FragmentArgsInherited
public class LightDetailsFragment extends LightFragment {

    public static LightDetailsFragment newInstance(String name) {
        LightDetailsFragment fragment = new LightDetailsFragment();

        Bundle args = new Bundle();
        args.putString(BaseFragment.FRAGMENT_ARGS_NAME, name);
        fragment.setArguments(args);

        return fragment;
    }

    public LightDetailsFragment() {
        super();
    }

    @InjectView(R.id.name_textview) TextView nameTextView;
    @InjectView(R.id.id_textview) TextView idTextView;
    @InjectView(R.id.hue_textview) TextView hueTextView;
    @InjectView(R.id.saturation_textview) TextView saturationTextView;
    @InjectView(R.id.brightness_textview) TextView brightnessTextView;
    @InjectView(R.id.kelvin_textview) TextView kelvinTextView;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_details;
    }

    @Override
    public LightPresenter createPresenter() {
        return new LightDetailsPresenter(getMainActivity().getComponent());
    }

    @Override
    public LightDetailsPresenter getPresenter() {
        return (LightDetailsPresenter) super.getPresenter();
    }

    @Override
    protected void initialiseViews(View view) { }

    @Override
    protected void setLightDetails() {
        nameTextView.setText(light.getName());
        idTextView.setText(light.id());
        hueTextView.setText(Integer.toString(light.getHue()));
        saturationTextView.setText(Integer.toString(light.getSaturation()));
        brightnessTextView.setText(Integer.toString(light.getBrightness()));
        kelvinTextView.setText(Integer.toString(light.getKelvin()));
    }

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
