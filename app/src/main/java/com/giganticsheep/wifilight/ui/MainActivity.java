package com.giganticsheep.wifilight.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.LightControlInterface;
import com.giganticsheep.wifilight.api.network.LightDataResponse;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.api.ModelConstants;
import com.giganticsheep.wifilight.ui.rx.ActivityLayout;
import com.giganticsheep.wifilight.ui.rx.FragmentAttachmentDetails;
import com.giganticsheep.wifilight.ui.rx.RXActivity;
import com.squareup.otto.Subscribe;

import java.util.List;

import rx.Observable;


public class MainActivity extends RXActivity {
    static final float DEFAULT_DURATION = 1.0F;

    private LightNetwork lightNetwork;

    private ViewPager viewPager;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lightNetwork = new LightNetwork(app.apiKey());

        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new LightFragmentPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        if (savedInstanceState == null) {
            // we are attaching the details fragment at position 0 which is under the view pager
            attachNewFragment(new FragmentAttachmentDetails(getString(R.string.fragment_name_light_details), 0, true));
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

    public class LightFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;

        public LightFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            try {
                switch(position) {
                    case 0:
                        return app.createFragment(getString(R.string.fragment_name_light_colour));
                    case 1:
                        return app.createFragment(getString(R.string.fragment_name_light_effects));
                    default:
                        return null;
                }
            } catch (Exception e) {
                e.printStackTrace();

                return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return getString(R.string.fragment_name_light_colour);
                case 1:
                    return getString(R.string.fragment_name_light_effects);
                default:
                    return null;
            }
        }
    }

    @Override
    public final String toString() {
        return "MainActivity{" +
                "lightNetwork=" + lightNetwork.toString() +
                '}';
    }

    /**
     * Created by anne on 26/06/15.
     * (*_*)
     */
    public static class LightNetworkController implements LightControlInterface {
        private final LightNetwork lightNetwork;

        public LightNetworkController(MainActivity activity) {
            lightNetwork = activity.lightNetwork;
        }

        @Override
        public Observable setHue(int hue, float duration) {
            return lightNetwork.setHue(hue, duration);
        }

        @Override
        public Observable setSaturation(int saturation, float duration) {
            return lightNetwork.setSaturation(saturation, duration);
        }

        @Override
        public Observable setBrightness(int brightness, float duration) {
            return lightNetwork.setBrightness(brightness, duration);
        }

        @Override
        public Observable setKelvin(int kelvin, float duration) {
            return lightNetwork.setKelvin(kelvin, duration);
        }

        @Override
        public Observable togglePower() {
            return lightNetwork.togglePower();
        }

        @Override
        public Observable setPower(ModelConstants.Power power, float duration) {
            return lightNetwork.setPower(power, duration);
        }

        @Override
        public Observable<List<LightDataResponse>> fetchLights() {
            return lightNetwork.fetchLights();
        }
    }
}
