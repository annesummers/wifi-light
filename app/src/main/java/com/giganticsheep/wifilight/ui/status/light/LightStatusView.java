package com.giganticsheep.wifilight.ui.status.light;

import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.ui.base.ViewBase;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 08/09/15. <p>
 * (*_*)
 */
public interface LightStatusView extends ViewBase {
    void showLight(Light light);
}
