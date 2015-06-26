package com.giganticsheep.wifilight.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.api.ModelConstants;
import com.giganticsheep.wifilight.ui.rx.RXFragment;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;
import com.squareup.otto.Subscribe;

import butterknife.InjectView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
@FragmentArgsInherited
public class LightColourFragment extends LightFragment {

    public static LightColourFragment newInstance(String name) {
        LightColourFragment fragment = new LightColourFragment();

        Bundle args = new Bundle();
        args.putString(RXFragment.FRAGMENT_ARGS_NAME, name);
        fragment.setArguments(args);

        return fragment;
    }

    public LightColourFragment() {
        super();
    }

    @InjectView(R.id.hue_seekbar) SeekBar hueSeekBar;
    @InjectView(R.id.saturation_seekbar) SeekBar saturationSeekBar;
    @InjectView(R.id.brightness_seekbar) SeekBar valueSeekBar;
    @InjectView(R.id.kelvin_seekbar) SeekBar kelvinSeekBar;
    @InjectView(R.id.power_toggle) ToggleButton powerToggle;

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new OnSeekBarChangeListener();

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_colour;
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        app.registerForEvents(this);
    }

    @Override
    protected void initialiseViews(View view) {
        logger.debug("initialiseViews()");

        // TODO sliders thumb drops off the end
        // TODO put toggle above pager
        // TODO colour of tabs

       // hueSeekBar = (SeekBar) view.findViewById(R.id.hue_seekbar);
        hueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

      //  saturationSeekBar = (SeekBar) view.findViewById(R.id.saturation_seekbar);
        saturationSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

       // valueSeekBar = (SeekBar) view.findViewById(R.id.brightness_seekbar);
        valueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

       // kelvinSeekBar = (SeekBar) view.findViewById(R.id.kelvin_seekbar);
        kelvinSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

      //  powerToggle = (ToggleButton) view.findViewById((R.id.power_toggle));
        powerToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Observable observable = null;

                if(isChecked && light != null && light.getPower() != ModelConstants.Power.ON) {
                    observable = lightController.setPower(ModelConstants.Power.ON, MainActivity.DEFAULT_DURATION);
                } else if(!isChecked && light != null && light.getPower() != ModelConstants.Power.OFF){
                    observable = lightController.setPower(ModelConstants.Power.OFF, MainActivity.DEFAULT_DURATION);
                }

                if(observable != null) {
                    observable
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            showToast(throwable.getMessage());
                        }

                        @Override
                        public void onNext(Object o) {
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void setLightDetails() {
        hueSeekBar.setProgress(light.getHue());
        saturationSeekBar.setProgress(light.getSaturation());
        valueSeekBar.setProgress(light.getBrightness());
        kelvinSeekBar.setProgress(light.getKelvin() - 2500);

        powerToggle.setChecked(light.getPower() == ModelConstants.Power.ON);
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

    private class OnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
            if(fromUser) {
                Observable observable = null;

                if (seekBar.equals(hueSeekBar)) {
                    observable = lightController.setHue(value, MainActivity.DEFAULT_DURATION);;
                } else if (seekBar.equals(saturationSeekBar)) {
                    observable = lightController.setSaturation(value, MainActivity.DEFAULT_DURATION);
                } else if (seekBar.equals(valueSeekBar)) {
                    observable = lightController.setBrightness(value, MainActivity.DEFAULT_DURATION);
                } else if (seekBar.equals(kelvinSeekBar)) {
                    observable = lightController.setKelvin(value, MainActivity.DEFAULT_DURATION);
                }

                if(observable != null) {
                    observable
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber() {
                                @Override
                                public void onCompleted() {
                                }

                                @Override
                                public void onError(Throwable throwable) {
                                    showToast(throwable.getMessage());
                                }

                                @Override
                                public void onNext(Object o) {
                                }
                            });
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { }
    }
}
