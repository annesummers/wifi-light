package com.giganticsheep.wifilight.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.model.LightNetwork;
import com.giganticsheep.wifilight.model.ModelConstants;
import com.giganticsheep.wifilight.ui.rx.RXFragment;
import com.squareup.otto.Subscribe;

import rx.Observable;

/**
 * Created by anne on 22/06/15.
 */
public class HSVFragment extends RXFragment {

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new OnSeekBarChangeListener();

    public static HSVFragment newInstance(String name) {
        HSVFragment fragment = new HSVFragment();

        Bundle args = new Bundle();
        args.putString(RXFragment.FRAGMENT_ARGS_NAME, name);
        fragment.setArguments(args);

        return fragment;
    }

    public HSVFragment() {
        super();
    }

    @Override
    protected void destroyViews() {
        WifiLightApplication.application().unregisterForEvents(this);
    }

    private SeekBar hueSeekBar;
    private SeekBar saturationSeekBar;
    private SeekBar valueSeekBar;

    private ToggleButton powerToggle;

    private TextView nameTextView;
    private TextView idTextView;
    private TextView hueTextView;
    private TextView saturationTextView;
    private TextView brightnessTextView;
    private TextView kelvinTextView;

    @Override
    protected int layoutId() {
        return R.layout.fragment_hsv;
    }

    @Override
    public Observable<RXFragment> layoutScreen(Configuration configuration) {
        return Observable.empty();
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
    }

    @Override
    protected void initialiseViews(View view) {
        hueSeekBar = (SeekBar) view.findViewById(R.id.hue_seekbar);
        hueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        saturationSeekBar = (SeekBar) view.findViewById(R.id.saturation_seekbar);
        saturationSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        valueSeekBar = (SeekBar) view.findViewById(R.id.value_seekbar);
        valueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        nameTextView = (TextView) view.findViewById(R.id.name_textview);
        idTextView = (TextView) view.findViewById(R.id.id_textview);
        hueTextView = (TextView) view.findViewById(R.id.hue_textview);
        saturationTextView = (TextView) view.findViewById(R.id.saturation_textview);
        brightnessTextView = (TextView) view.findViewById(R.id.brightness_textview);
        kelvinTextView = (TextView) view.findViewById(R.id.kelvin_textview);

        powerToggle = (ToggleButton) view.findViewById((R.id.power_toggle));
        powerToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    getMainActivity().setPower(ModelConstants.Power.ON);
                } else {
                    getMainActivity().setPower(ModelConstants.Power.OFF);
                }
            }
        });

        WifiLightApplication.application().registerForEvents(this);
    }

    @Subscribe
    public void lightChange(LightNetwork.LightDetailsEvent event) {
        int hue =(int)event.light().getHue();
        int saturation = (int)event.light().getSaturation()*100;
        int brightness = (int)event.light().getBrightness()*100;

        nameTextView.setText(event.light().getName());
        idTextView.setText(event.light().id());
        hueTextView.setText(Integer.toString(hue));
        saturationTextView.setText(Integer.toString(saturation));
        brightnessTextView.setText(Integer.toString(brightness));
        kelvinTextView.setText(Integer.toString((int) event.light().getKelvin()));

        hueSeekBar.setProgress(hue);
        saturationSeekBar.setProgress(saturation);
        valueSeekBar.setProgress(brightness);

        powerToggle.setChecked(event.light().getPower() == ModelConstants.Power.ON ? true : false);
    }

    private class OnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
            if(fromUser) {
                if (seekBar.equals(hueSeekBar)) {
                    getMainActivity().setHue(value);
                    return;
                }

                if (seekBar.equals(saturationSeekBar)) {
                    getMainActivity().setSaturation(value);
                    return;
                }

                if (seekBar.equals(valueSeekBar)) {
                    getMainActivity().setBrightness(value);
                    return;
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { }
    }

    public MainActivity getMainActivity() {
        return (MainActivity)activity();
    }
}
