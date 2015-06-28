package com.giganticsheep.wifilight.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.api.ModelConstants;
import com.giganticsheep.wifilight.ui.rx.BaseFragment;
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
        args.putString(BaseFragment.FRAGMENT_ARGS_NAME, name);
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        eventBus.registerForEvents(this);
    }

    @Override
    protected void initialiseViews(View view) {
        logger.debug("initialiseViews()");

        // TODO sliders thumb drops off the end
        // TODO put toggle above pager
        // TODO colour of tabs

        hueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        saturationSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        valueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        kelvinSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        powerToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Observable observable = null;

                if(isChecked && light != null && light.getPower() != ModelConstants.Power.ON) {
                    observable = lightNetwork.setPower(ModelConstants.Power.ON, MainActivity.DEFAULT_DURATION);
                } else if(!isChecked && light != null && light.getPower() != ModelConstants.Power.OFF){
                    observable = lightNetwork.setPower(ModelConstants.Power.OFF, MainActivity.DEFAULT_DURATION);
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
    protected void destroyViews() { }

    @Override
    public void onDestroy() {
        super.onDestroy();

        eventBus.unregisterForEvents(this);
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
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
                    observable = lightNetwork.setHue(value, MainActivity.DEFAULT_DURATION);;
                } else if (seekBar.equals(saturationSeekBar)) {
                    observable = lightNetwork.setSaturation(value, MainActivity.DEFAULT_DURATION);
                } else if (seekBar.equals(valueSeekBar)) {
                    observable = lightNetwork.setBrightness(value, MainActivity.DEFAULT_DURATION);
                } else if (seekBar.equals(kelvinSeekBar)) {
                    observable = lightNetwork.setKelvin(value, MainActivity.DEFAULT_DURATION);
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