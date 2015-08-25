package com.giganticsheep.wifilight.ui.base.light;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.widget.SeekBar;

import java.util.Map;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/07/15. <p>
 * (*_*)
 */
public class OnLightSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

    private boolean tracking = false;

    private final Map<SeekBar, Integer> values = new ArrayMap<>();

    private final LightSeekBarWrapper lightSeekBarWrapper;

    public OnLightSeekBarChangeListener(@NonNull final LightSeekBarWrapper lightSeekBar) {
        this.lightSeekBarWrapper = lightSeekBar;
    }

    @Override
    public void onProgressChanged(@NonNull final SeekBar seekBar,
                                  final int value,
                                  boolean fromUser) {
        if (fromUser) {
            values.put(seekBar, value);
        }
    }

    @Override
    public synchronized void onStartTrackingTouch(@NonNull final SeekBar seekBar) {
        tracking = true;
    }

    @Override
    public synchronized void onStopTrackingTouch(@NonNull final SeekBar seekBar) {
        tracking = false;

        if (values.containsKey(seekBar)) {
            int value = values.get(seekBar);

            lightSeekBarWrapper.setLightValue(seekBar, value);

            values.remove(seekBar);
        }
    }
}
