package com.giganticsheep.wifilight.ui.fragment;

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
import com.giganticsheep.wifilight.mvp.presenter.LightPresenterBase;
import com.giganticsheep.wifilight.mvp.presenter.LightStatusPresenter;
import com.giganticsheep.wifilight.ui.LightControlActivity;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;

import butterknife.InjectView;
import butterknife.OnCheckedChanged;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 17/07/15. <p>
 * (*_*)
 */
@FragmentArgsInherited
public class LightStatusFragment extends LightFragmentBase {

    @InjectView(R.id.power_toggle) ToggleButton powerToggle;
    @InjectView(R.id.status_textview) TextView statusTextView;

    public LightStatusFragment() {
        super();
    }

    @NonNull
    @Override
    public LightPresenterBase createPresenter() {
        return new LightStatusPresenter(getLightControlActivity().getComponent(),
                getLightControlActivity().getPresenter());
    }

    @NonNull
    public LightStatusPresenter getLightStatusPresenter() {
        return (LightStatusPresenter) super.getPresenter();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_status;
    }

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

    @Override
    protected void enableViews(boolean enable) {
        powerToggle.setEnabled(enable);
    }

    @OnCheckedChanged(R.id.power_toggle)
    public void onPowerToggle(CompoundButton compoundButton, boolean isChecked) {
        Light light = getPresenter().getLight();

        if(isChecked && light != null && light.getPower() != LightControl.Power.ON) {
            getLightStatusPresenter().setPower(LightControl.Power.ON, LightControlActivity.DEFAULT_DURATION);
        } else if(!isChecked && light != null && light.getPower() != LightControl.Power.OFF){
            getLightStatusPresenter().setPower(LightControl.Power.OFF, LightControlActivity.DEFAULT_DURATION);
        }
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
    }
}
