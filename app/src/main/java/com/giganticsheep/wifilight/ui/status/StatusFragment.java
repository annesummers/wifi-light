package com.giganticsheep.wifilight.ui.status;

import android.support.annotation.NonNull;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidanimations.library.attention.ShakeAnimator;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.ui.base.light.LightPresenterBase;
import com.giganticsheep.wifilight.ui.base.light.LightFragmentBase;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;

import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import hugo.weaving.DebugLog;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 17/07/15. <p>
 * (*_*)
 */
@FragmentArgsInherited
public class StatusFragment extends LightFragmentBase {

    @InjectView(R.id.power_toggle) ToggleButton powerToggle;
    @InjectView(R.id.status_textview) TextView statusTextView;

    public StatusFragment() {
        super();
    }

    @NonNull
    @Override
    public LightPresenterBase createPresenter() {
        return new StatusPresenter(getLightControlActivity().getComponent());
    }

    @NonNull
    private StatusPresenter getLightStatusPresenter() {
        return (StatusPresenter) super.getPresenter();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_status;
    }

    @DebugLog
    @Override
    protected void showLight(Light light) {
        powerToggle.setChecked(light.getPower() == LightControl.Power.ON);

        String oldStatus = (String) statusTextView.getText();
        statusTextView.setText(getString(light.isConnected() ? R.string.label_light_connected
                                                             : R.string.label_light_disconnected));

        if(!oldStatus.equals(statusTextView.getText())) {
            YoYo.with(Techniques.Shake)
                    .duration(ShakeAnimator.DURATION)
                    .playOn(statusTextView);
        }
    }

    @DebugLog
    @Override
    protected void enableViews(boolean enable) {
        powerToggle.setEnabled(enable);
    }

    @DebugLog
    @OnCheckedChanged(R.id.power_toggle)
    public void onPowerToggle(CompoundButton compoundButton, boolean isChecked) {
        getLightStatusPresenter().setPower(isChecked);
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
    }
}
