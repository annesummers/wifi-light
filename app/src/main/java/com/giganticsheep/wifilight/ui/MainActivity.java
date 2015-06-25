package com.giganticsheep.wifilight.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.model.Light;
import com.giganticsheep.wifilight.model.LightNetwork;
import com.giganticsheep.wifilight.model.ModelConstants;
import com.giganticsheep.wifilight.ui.rx.ActivityLayout;
import com.giganticsheep.wifilight.ui.rx.RXActivity;
import com.squareup.otto.Subscribe;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action1;


public class MainActivity extends RXActivity {
    private static final float DEFAULT_DURATION = 1.0F;

    private LightNetwork lightNetwork;

    //private ViewPager viewpager;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lightNetwork = new LightNetwork(WifiLightApplication.application().apiKey());

        setContentView(R.layout.activity_main);

      //  viewpager = (ViewPager) findViewById(R.id.pager);

        if (savedInstanceState == null) {
            attachNewFragment(new FragmentAttachmentDetails(WifiLightApplication.FRAGMENT_NAME_HSVFRAGMENT, 0, true));
        }
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

    public final void setHue(final int hue) {
        compositeSubscription.add(bind(lightNetwork.setHue(hue, DEFAULT_DURATION))
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable throwable) {
                        showToast(throwable.getMessage());
                    }

                    @Override
                    public void onNext(Object o) { }
                }));
    }

    public final void setSaturation(final int saturation) {
        compositeSubscription.add(bind(lightNetwork.setSaturation(saturation, DEFAULT_DURATION))
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable throwable) {
                        showToast(throwable.getMessage());
                    }

                    @Override
                    public void onNext(Object o) { }
                }));
    }

    public final void setBrightness(final int brightness) {
        compositeSubscription.add(bind(lightNetwork.setBrightness(brightness, DEFAULT_DURATION))
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
                }));
    }

    public final void setKelvin(final int kelvin) {
        compositeSubscription.add(bind(lightNetwork.setKelvin(kelvin, DEFAULT_DURATION))
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable throwable) {
                        showToast(throwable.getMessage());
                    }

                    @Override
                    public void onNext(Object o) { }
                }));
    }

    public final void togglePower() {
        compositeSubscription.add(bind(lightNetwork.toggleLights())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable throwable) {
                        showToast(throwable.getMessage());
                    }

                    @Override
                    public void onNext(Object o) { }
                }));
    }

    public final void setPower(ModelConstants.Power power) {
        compositeSubscription.add(bind(lightNetwork.setPower(power, DEFAULT_DURATION))
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable throwable) {
                        showToast(throwable.getMessage());
                    }

                    @Override
                    public void onNext(Object o) { }
                }));
    }

    public final void fetchLights() {
        compositeSubscription.add(bind(lightNetwork.fetchLights())
                .subscribe());
    }

   /* public class LightFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 3;
        private String tabTitles[] = new String[] { "Tab1", "Tab2", "Tab3" };
        private Context context;

        public LightFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position + 1);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }

    @Override
    public final String toString() {
        return "MainActivity{" +
                "lightNetwork=" + lightNetwork.toString() +
                '}';
    }*/
}
