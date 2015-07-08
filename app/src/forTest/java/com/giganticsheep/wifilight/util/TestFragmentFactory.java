package com.giganticsheep.wifilight.util;

import com.giganticsheep.wifilight.ui.base.BaseApplication.FragmentFactory;
import com.giganticsheep.wifilight.ui.base.BaseFragment;

import rx.Observable;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */
public class TestFragmentFactory implements FragmentFactory {
    @Override
    public Observable<? extends BaseFragment> createFragmentAsync(String name) {
        return null;
    }

    @Override
    public BaseFragment createFragment(String name) throws Exception {
        return null;
    }
}
