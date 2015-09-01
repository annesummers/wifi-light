package com.giganticsheep.wifilight.ui.white;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.SeekBar;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.base.ColourSeekBar;
import com.giganticsheep.wifilight.ui.base.light.LightFragmentBase;
import com.giganticsheep.wifilight.ui.base.light.LightPresenterBase;
import com.giganticsheep.wifilight.ui.base.light.LightSeekBarWrapper;
import com.giganticsheep.wifilight.ui.base.light.OnLightSeekBarChangeListener;
import com.giganticsheep.wifilight.ui.control.LightControlActivity;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;

import butterknife.InjectView;
import hugo.weaving.DebugLog;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/07/15. <p>
 * (*_*)
 */
@FragmentArgsInherited
public class WhiteFragment extends LightFragmentBase {

    @InjectView(R.id.brightness_seekbar) SeekBar brightnessSeekbar;
    @InjectView(R.id.kelvin_seekbar)
    ColourSeekBar kelvinSeekBar;

    public WhiteFragment() {
        super();

        seekBarChangeListener = new OnLightSeekBarChangeListener(new WhiteSeekBarWrapper());
    }

    @NonNull
    @Override
    public LightPresenterBase createPresenter() {
        return new WhitePresenter(getLightControlActivity().getComponent());
    }

    @NonNull
    private WhitePresenter getLightWhitePresenter() {
        return (WhitePresenter) super.getPresenter();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_white;
    }

    @DebugLog
    @Override
    protected void initialiseViews(@NonNull final View view) {
        brightnessSeekbar.setOnSeekBarChangeListener(seekBarChangeListener);
       // kelvinSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    @DebugLog
    @Override
    public void showLight(@NonNull final Light light) {
        brightnessSeekbar.setProgress(light.getBrightness());
      //  kelvinSeekBar.setProgress(light.getKelvin() - LightConstants.KELVIN_BASE);
    }

    @Override
    protected void enableViews(boolean enable) {
        brightnessSeekbar.setEnabled(enable);
        kelvinSeekBar.setEnabled(enable);
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
    }

    private class WhiteSeekBarWrapper implements LightSeekBarWrapper {

        @Override
        public void setLightValue(@NonNull final SeekBar seekBar,
                                  final int value) {

            if (seekBar.equals(brightnessSeekbar)) {
                getLightWhitePresenter().setBrightness(value, LightControlActivity.DEFAULT_DURATION);
            } else if (seekBar.equals(kelvinSeekBar)) {
                getLightWhitePresenter().setKelvin(value, LightControlActivity.DEFAULT_DURATION);
            }
        }
    }
}
