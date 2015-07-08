package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.api.network.NetworkDetails;
import com.giganticsheep.wifilight.di.HasComponent;
import com.giganticsheep.wifilight.ui.fragment.LightColourFragment;
import com.giganticsheep.wifilight.ui.fragment.LightDetailsFragment;
import com.giganticsheep.wifilight.ui.fragment.LightEffectsFragment;
import com.giganticsheep.wifilight.ui.base.BaseApplication;
import com.giganticsheep.wifilight.ui.base.BaseFragment;

import org.jetbrains.annotations.NonNls;

import rx.Observable;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class WifiLightApplication extends BaseApplication implements HasComponent<WifiLightAppComponent> {

    @NonNls private static final String DEFAULT_SERVER_STRING = "https://api.lifx.com";
    @NonNls private static final String DEFAULT_URL_STRING1 = "v1beta1";
    @NonNls private static final String DEFAULT_URL_STRING2 = "lights";

    private NetworkDetails networkDetails;
    private WifiLightAppComponent wifiLightAppComponent;

    @Override
    public final void onCreate() {
        super.onCreate();

        networkDetails = new NetworkDetails(
                getResources().getString(R.string.DEFAULT_API_KEY),
                DEFAULT_SERVER_STRING,
                DEFAULT_URL_STRING1,
                DEFAULT_URL_STRING2);

        buildComponentAndInject();
    }

    public NetworkDetails getNetworkDetails() {
        return networkDetails;
    }

    @Override
    public FragmentFactory createFragmentFactory() {
        return new FragmentFactoryImpl();
    }

    public void buildComponentAndInject() {
        wifiLightAppComponent = WifiLightAppComponent.Initializer.init(this);
        wifiLightAppComponent.inject(this);
    }

    public WifiLightAppComponent getComponent() {
        return wifiLightAppComponent;
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
