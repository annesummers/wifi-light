package com.giganticsheep.wifilight.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.model.LightNetwork;
import com.giganticsheep.wifilight.model.ModelConstants;
import com.giganticsheep.wifilight.ui.rx.ActivityLayout;
import com.giganticsheep.wifilight.ui.rx.RXActivity;
import com.squareup.otto.Subscribe;

import rx.functions.Action1;


public class MainActivity extends RXActivity {
    private static final float DEFAULT_DURATION = 1.0F;

    private LightNetwork lightNetwork;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lightNetwork = new LightNetwork(WifiLightApplication.application().apiKey());

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            attachNewFragment(WifiLightApplication.FRAGMENT_NAME_HSVFRAGMENT, 0, true);
        }
    }

    @Override
    protected void initialiseViews() { }

    @Override
    protected final ActivityLayout createActivityLayout() {
        return new ActivityLayout() {
            @Override
            public int fragmentContainer(final int position) {
                switch (position) {
                    case 0:
                        return R.id.container;
                    default:
                        return 0;

                }
            }

            @Override
            public int fragmentContainerCount() {
                return 1;
            }
        };
    }


    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public final boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void lightChangeSuccess(LightNetwork.SuccessEvent event) {
        showToast("Changed!");
    }

    public final void setHue(final int hue) {
        compositeSubscription.add(lightNetwork.setHue(hue, DEFAULT_DURATION)
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        showToast(throwable.getMessage());
                    }
                })
                .subscribe());
    }

    public final void setSaturation(final int saturation) {
        compositeSubscription.add(lightNetwork.setSaturation(saturation, DEFAULT_DURATION)
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        showToast(throwable.getMessage());
                    }
                })
                .subscribe());
    }

    public final void setBrightness(final int brightness) {
        compositeSubscription.add(lightNetwork.setBrightness(brightness, DEFAULT_DURATION)
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        showToast(throwable.getMessage());
                    }
                })
                .subscribe());
    }

    public final void setKelvin(final int kelvin) {
        compositeSubscription.add(lightNetwork.setKelvin(kelvin, DEFAULT_DURATION)
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        showToast(throwable.getMessage());
                    }
                })
                .subscribe());
    }

    public final void togglePower() {
        compositeSubscription.add(lightNetwork.toggleLights()
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        showToast(throwable.getMessage());
                    }
                })
                .subscribe());
    }

    public final void setPower(ModelConstants.Power power) {
        compositeSubscription.add(lightNetwork.setPower(power, DEFAULT_DURATION)
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        showToast(throwable.getMessage());
                    }
                })
                .subscribe());
    }

    @Override
    public final String toString() {
        return "MainActivity{" +
                "lightNetwork=" + lightNetwork.toString() +
                '}';
    }
}
