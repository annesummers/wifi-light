package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.api.network.NetworkDetails;
import com.giganticsheep.wifilight.di.components.BaseApplicationComponent;
import com.giganticsheep.wifilight.di.components.DaggerWifiApplicationComponent;
import com.giganticsheep.wifilight.di.components.WifiApplicationComponent;
import com.giganticsheep.wifilight.di.modules.BaseApplicationModule;
import com.giganticsheep.wifilight.di.modules.WifiApplicationModule;
import com.giganticsheep.wifilight.ui.LightColourFragment;
import com.giganticsheep.wifilight.ui.LightDetailsFragment;
import com.giganticsheep.wifilight.ui.LightEffectsFragment;
import com.giganticsheep.wifilight.ui.rx.BaseApplication;
import com.giganticsheep.wifilight.ui.rx.BaseFragment;

import org.jetbrains.annotations.NonNls;

import rx.Observable;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class WifiLightApplication extends BaseApplication {

    @NonNls private static final String DEFAULT_API_KEY = "c5e3c4b06448baa75d3a849b7cdb70930e4b95e9e7160a4415c49bf03ffa45f8";
    @NonNls private static final String DEFAULT_SERVER_STRING = "https://api.lifx.com";
    @NonNls private static final String DEFAULT_URL_STRING1 = "v1beta1";
    @NonNls private static final String DEFAULT_URL_STRING2 = "lights";

    private NetworkDetails networkDetails;
    private WifiApplicationComponent wifiApplicationComponent;

    @Override
    public final void onCreate() {
        super.onCreate();

        // TODO private api key
        networkDetails = new NetworkDetails(DEFAULT_API_KEY,
        DEFAULT_SERVER_STRING,
                DEFAULT_URL_STRING1,
                DEFAULT_URL_STRING2);
    }

    public NetworkDetails getNetworkDetails() {
        return networkDetails;
    }

    @Override
    protected FragmentFactory createFragmentFactory() {
        return new FragmentFactoryImpl();
    }

    @Override
    protected BaseApplicationComponent createApplicationComponent() {
        return DaggerWifiApplicationComponent
                .builder()
                .baseApplicationModule(new BaseApplicationModule(this))
                .wifiApplicationModule(new WifiApplicationModule())
                .build();
    }

    private class FragmentFactoryImpl implements FragmentFactory {
        /**
         * @param name the name of the fragment to create
         * @return the Observable to subscribe to
         */
        public final Observable<? extends BaseFragment> createFragmentAsync(final String name) {
            if(name.equals(getString(R.string.fragment_name_light_colour))) {
                return Observable.just(LightColourFragment.newInstance(name));
            }

            if(name.equals(getString(R.string.fragment_name_light_effects))) {
                return Observable.just(LightEffectsFragment.newInstance(name));
            }

            if(name.equals(getString(R.string.fragment_name_light_details))) {
                return Observable.just(LightDetailsFragment.newInstance(name));
            }

            return Observable.error(new Exception("Fragment does not exist"));
        }

        /**
         * @param name the name of the fragment to create
         * @return the Observable to subscribe to
         */
        public final BaseFragment createFragment(final String name) throws Exception {
            if(name.equals(getString(R.string.fragment_name_light_colour))) {
                return LightColourFragment.newInstance(name);
            }

            if(name.equals(getString(R.string.fragment_name_light_effects))) {
                return LightEffectsFragment.newInstance(name);
            }

            if(name.equals(getString(R.string.fragment_name_light_details))) {
                return LightDetailsFragment.newInstance(name);
            }

            throw new Exception("Fragment does not exist");
        }

    }
}
