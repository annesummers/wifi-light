package com.giganticsheep.wifilight.base;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.ui.base.BaseFragment;
import com.giganticsheep.wifilight.ui.fragment.LightColourFragment;
import com.giganticsheep.wifilight.ui.fragment.LightDetailsFragment;
import com.giganticsheep.wifilight.ui.fragment.LightEffectsFragment;

import rx.Observable;

/**
 * Created by anne on 09/07/15.
 * (*_*)
 */
public class FragmentFactory {

    private final WifiLightApplication application;

    public FragmentFactory(WifiLightApplication application) {
        this.application = application;
    }

    public FragmentFactory() {
        this.application = null;
    }

    /**
     * @param name the name of the fragment to create
     * @return the Observable to subscribe to
     */
    public final Observable<? extends BaseFragment> createFragmentAsync(final String name) {
        try {
            return Observable.just(createFragment(name));
        } catch (Exception e) {
            e.printStackTrace();
            return Observable.error(e);
        }
    }

    /**
     * @param name the name of the fragment to create
     * @return the Observable to subscribe to
     */
    public final BaseFragment createFragment(final String name) throws Exception {
        if(application != null) {
            if (name.equals(application.getString(R.string.fragment_name_light_colour))) {
                return LightColourFragment.newInstance(name);
            }

            if (name.equals(application.getString(R.string.fragment_name_light_effects))) {
                return LightEffectsFragment.newInstance(name);
            }

            if (name.equals(application.getString(R.string.fragment_name_light_details))) {
                return LightDetailsFragment.newInstance(name);
            }
        }

        throw new Exception("Fragment does not exist");
    }
}
