package com.giganticsheep.wifilight.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.ui.rx.RXFragment;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;
import com.squareup.otto.Subscribe;

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
        args.putString(RXFragment.FRAGMENT_ARGS_NAME, name);
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
    protected boolean reinitialiseOnRotate() {
        return false;
    }

   /* @Override
    protected void initialiseData(Bundle savedInstanceState) {
        super.initialiseData(savedInstanceState);

        //app.registerForEvents(this);
    }*/

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        app.registerForEvents(this);
    }

    @Override
    protected void initialiseViews(View view) {
        logger.debug("initialiseViews()");
    }

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
    protected void destroyViews() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        app.unregisterForEvents(this);
    }

    @Subscribe
    public void lightChange(LightNetwork.LightDetailsEvent event) {
        logger.debug("lightChange()");

        handleLightChange(event.light());
    }
}
