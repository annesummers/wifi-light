package com.giganticsheep.wifilight.ui.navigation;

import android.os.Parcel;

import com.giganticsheep.nofragmentbase.ui.base.Screen;
import com.giganticsheep.nofragmentbase.ui.base.ScreenGroup;
import com.giganticsheep.wifilight.R;

/**
 * Created by anne on 15/02/16.
 */
public class SplashScreen extends Screen {

    protected SplashScreen(ScreenGroup screenGroup) {
        super(screenGroup);
    }

    protected SplashScreen(Parcel in) {
        super(in);
    }

    @Override
    protected void injectDependencies() { }

    @Override
    protected int layoutId() {
        return R.layout.view_splash;
    }

    @Override
    protected void showData() { }
}
