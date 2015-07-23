package com.giganticsheep.wifilight.ui.fragment;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.SeekBar;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightConstants;
import com.giganticsheep.wifilight.mvp.presenter.LightPresenterBase;
import com.giganticsheep.wifilight.mvp.presenter.WhitePresenter;
import com.giganticsheep.wifilight.ui.LightControlActivity;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;

import butterknife.InjectView;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/07/15. <p>
 * (*_*)
 */
@FragmentArgsInherited
public class WhiteFragment extends LightFragmentBase {

    @InjectView(R.id.brightness_seekbar) SeekBar valueSeekBar;
    @InjectView(R.id.kelvin_seekbar) SeekBar kelvinSeekBar;

    @NonNull
    @Override
    public LightPresenterBase createPresenter() {
        return new WhitePresenter(getLightControlActivity().getComponent(),
                getLightControlActivity().getPresenter());
    }

    @NonNull
    private WhitePresenter getLightWhitePresenter() {
        return (WhitePresenter) super.getPresenter();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_white;
    }

    @Override
    protected void initialiseViews(View view) {
        valueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        kelvinSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    @Override
    protected void showLight(Light light) {
        valueSeekBar.setProgress(light.getBrightness());
        kelvinSeekBar.setProgress(light.getKelvin() - LightConstants.KELVIN_BASE);
    }

    @Override
    protected void enableViews(boolean enable) {
        valueSeekBar.setEnabled(enable);
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

            if (seekBar.equals(valueSeekBar)) {
                getLightWhitePresenter().setBrightness(value, LightControlActivity.DEFAULT_DURATION);
            } else if (seekBar.equals(kelvinSeekBar)) {
                getLightWhitePresenter().setKelvin(value, LightControlActivity.DEFAULT_DURATION);
            }
        }
    }
}
