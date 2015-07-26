package com.giganticsheep.wifilight.ui.details;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.ui.base.light.LightPresenterBase;
import com.giganticsheep.wifilight.ui.base.light.LightFragmentBase;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;

import butterknife.InjectView;
import hugo.weaving.DebugLog;

/**
 * Created by anne on 25/06/15.
 * (*_*)
 */

@FragmentArgsInherited
public class DetailsFragment extends LightFragmentBase {

    public DetailsFragment() {
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

    @NonNull
    @Override
    public LightPresenterBase createPresenter() {
        return new DetailsPresenter(getLightControlActivity().getComponent());
    }

    @Override
    protected void initialiseViews(View view) { }

    @DebugLog
    @Override
    protected void showLight(Light light) {
        nameTextView.setText(light.getLabel());
        idTextView.setText(light.id());
        hueTextView.setText(Integer.toString(light.getHue()));
        saturationTextView.setText(Integer.toString(light.getSaturation()));
        brightnessTextView.setText(Integer.toString(light.getBrightness()));
        kelvinTextView.setText(Integer.toString(light.getKelvin()));
    }

    @Override
    protected void enableViews(boolean enable) { }

    @Override
    protected void destroyViews() { }

    @DebugLog
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
