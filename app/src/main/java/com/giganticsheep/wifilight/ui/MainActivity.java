package com.giganticsheep.wifilight.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.model.LightNetwork;
import com.giganticsheep.wifilight.ui.rx.ActivityLayout;
import com.giganticsheep.wifilight.ui.rx.RXActivity;



public class MainActivity extends RXActivity {

    private LightNetwork mLightNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLightNetwork = new LightNetwork(WifiLightApplication.application().aPIKey());

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            attachNewFragment(WifiLightApplication.FRAGMENT_NAME_HSVFRAGMENT, 0, true);
        }
    }

    @Override
    protected void initialiseViews() {


    }

    @Override
    protected ActivityLayout createActivityLayout() {
        return new ActivityLayout() {
            @Override
            public int fragmentContainer(int position) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setHue(int hue) {
        mLightNetwork.setHue(hue);
    }

    public void setSaturation(int saturation) {
        mLightNetwork.setSaturation(saturation);
    }

    public void setValue(int value) {
        mLightNetwork.setValue(value);
    }

    @Override
    public String toString() {
        return "MainActivity{" +
                "mLightNetwork=" + mLightNetwork.toString() +
                '}';
    }
}
