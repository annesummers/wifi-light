package com.giganticsheep.wifilight.api.model;

import com.giganticsheep.wifilight.AndroidEventBus;
import com.giganticsheep.wifilight.BaseTest;
import com.giganticsheep.wifilight.TestEventBus;
import com.giganticsheep.wifilight.WifiLightTest;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.api.network.LightService;
import com.giganticsheep.wifilight.api.network.NetworkDetails;
import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.base.EventBus;

import org.jetbrains.annotations.NonNls;

import retrofit.ErrorHandler;
import retrofit.MockRestAdapter;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public class TestLightNetwork extends LightNetwork {

    @NonNls public static final String DEFAULT_API_KEY = "c5e3c4b06448baa75d3a849b7cdb70930e4b95e9e7160a4415c49bf03ffa45f8";
    @NonNls public static final String DEFAULT_SERVER_STRING = "https://api.lifx.com";
    @NonNls public static final String DEFAULT_URL_STRING1 = "v1beta1";
    @NonNls public static final String DEFAULT_URL_STRING2 = "lights";

    private final WifiLightTest test;

    private MockRestAdapter mockRestAdapter;

    /**
     * @param networkDetails
     * @param eventBus
     * @param baseLogger
     */
    public TestLightNetwork(NetworkDetails networkDetails,
                            EventBus eventBus,
                            BaseLogger baseLogger,
                            WifiLightTest test) {
        super(networkDetails, eventBus, baseLogger);

        this.test = test;
    }

    /**
     * @return
     */
    @Override
    protected LightService createLightService() {
        mockRestAdapter = MockRestAdapter.from(createRestAdapter());
        mockRestAdapter.setErrorPercentage(20);
        return mockRestAdapter.create(LightService.class, new MockLightService(test));
    }

    /**
     * @return
     */
    @Override
    protected RestAdapter createRestAdapter() {
        return new RestAdapter.Builder()
                .setEndpoint("mock:///")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setErrorHandler(new ErrorHandler() {
                    @Override
                    public Throwable handleError(RetrofitError cause) {
                        test.signal();

                        return new Exception();
                    }
                })
                .build();
    }
}
