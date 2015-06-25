package com.giganticsheep.wifilight.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.model.LightNetwork;
import com.giganticsheep.wifilight.ui.rx.RXFragment;
import com.squareup.otto.Subscribe;

/**
 * Created by anne on 25/06/15.
 * (*_*)
 */
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

    private TextView nameTextView;
    private TextView idTextView;
    private TextView hueTextView;
    private TextView saturationTextView;
    private TextView brightnessTextView;
    private TextView kelvinTextView;

    @Override
    protected int layoutId() {
        return R.layout.fragment_details;
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
    }

    @Override
    protected void initialiseData(Bundle savedInstanceState) {
        super.initialiseData(savedInstanceState);

        WifiLightApplication.application().registerForEvents(this);
    }

    @Override
    protected void initialiseViews(View view) {
        logger.debug("initialiseViews()");

        nameTextView = (TextView) view.findViewById(R.id.name_textview);
        idTextView = (TextView) view.findViewById(R.id.id_textview);
        hueTextView = (TextView) view.findViewById(R.id.hue_textview);
        saturationTextView = (TextView) view.findViewById(R.id.saturation_textview);
        brightnessTextView = (TextView) view.findViewById(R.id.brightness_textview);
        kelvinTextView = (TextView) view.findViewById(R.id.kelvin_textview);

        if(light != null) {
            setLightDetails();
        }
    }

    @Override
    protected void setLightDetails() {
        int hue = (int)light.getHue();
        int saturation = (int)(light.getSaturation()*100);
        int brightness = (int)(light.getBrightness()*100);
        int kelvin = (int) light.getKelvin();

        nameTextView.setText(light.getName());
        idTextView.setText(light.id());
        hueTextView.setText(Integer.toString(hue));
        saturationTextView.setText(Integer.toString(saturation));
        brightnessTextView.setText(Integer.toString(brightness));
        kelvinTextView.setText(Integer.toString(kelvin));
    }

    @Override
    protected void destroyViews() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        WifiLightApplication.application().unregisterForEvents(this);
    }

    @Subscribe
    public void lightChange(LightNetwork.LightDetailsEvent event) {
        logger.debug("lightChange()");

        handleLightChange(event.light());
    }
}
