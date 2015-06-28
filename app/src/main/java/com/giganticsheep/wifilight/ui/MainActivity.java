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
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.api.network.NetworkDetails;
import com.giganticsheep.wifilight.di.HasComponent;
import com.giganticsheep.wifilight.di.components.DaggerMainActivityComponent;
import com.giganticsheep.wifilight.di.components.MainActivityComponent;
import com.giganticsheep.wifilight.di.components.WifiApplicationComponent;
import com.giganticsheep.wifilight.di.modules.MainActivityModule;
import com.giganticsheep.wifilight.ui.rx.ActivityLayout;
import com.giganticsheep.wifilight.ui.rx.BaseApplication;
import com.giganticsheep.wifilight.ui.rx.BaseLogger;
import com.giganticsheep.wifilight.ui.rx.FragmentAttachmentDetails;
import com.giganticsheep.wifilight.ui.rx.BaseActivity;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;


public class MainActivity extends BaseActivity implements HasComponent<MainActivityComponent> {
    static final float DEFAULT_DURATION = 1.0F;

    private ViewPager viewPager;
    private MainActivityComponent mainActivityComponent;

    @Inject NetworkDetails networkDetails;
    @Inject BaseApplication.EventBus eventBus;
    @Inject BaseLogger baseLogger;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    protected void injectDependencies() {
        super.injectDependencies();

        WifiLightApplication application = ((WifiLightApplication)getApplication());

        mainActivityComponent = DaggerMainActivityComponent.builder()
                .wifiApplicationComponent((WifiApplicationComponent) application.getApplicationComponent())
                .baseActivityModule(getBaseActivityModule())
                .mainActivityModule(new MainActivityModule())//networkDetails, eventBus, baseLogger))
                .build();
    }

    @Override
    public MainActivityComponent getComponent() {
        return mainActivityComponent;
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

    @Subscribe
    public void lightChangeSuccess(LightNetwork.SuccessEvent event) {
        showToast("Changed!");
    }

    private class LightFragmentPagerAdapter extends FragmentPagerAdapter {
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
                        return fragmentFactory.createFragment(getString(R.string.fragment_name_light_colour));
                    case 1:
                        return fragmentFactory.createFragment(getString(R.string.fragment_name_light_effects));
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
}
