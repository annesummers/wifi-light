package com.giganticsheep.wifilight.ui.control;

import com.giganticsheep.nofragmentbase.dagger.HasComponent;
import com.giganticsheep.wifilight.ui.base.WifiLightScreenGroup;

/**
 * Created by anne on 19/02/16.
 */
public class LightControlScreenGroup extends WifiLightScreenGroup
                                    implements HasComponent<LightControlActivityComponent> {

    private final LightControlActivityComponent component;

    public LightControlScreenGroup(LightControlActivityComponent component) {
        this.component = component;
        this.component.inject(this);

        eventBus.registerForEvents(this);
    }

    @Override
    public LightControlActivityComponent getComponent() {
        return component;
    }

    public interface Injector {
        void inject(LightControlScreenGroup screenGroup);
    }
}
