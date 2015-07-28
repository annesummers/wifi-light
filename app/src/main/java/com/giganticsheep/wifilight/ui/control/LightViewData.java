package com.giganticsheep.wifilight.ui.control;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 28/07/15. <p>
 * (*_*)
 */
public class LightViewData extends LightNetworkPresenter.ListItemData {
    private final boolean connected;
    private final String groupId;

    public LightViewData(final String id,
                         final String label,
                         final boolean connected,
                         final String groupId) {
        super(id, label);

        this.connected = connected;
        this.groupId = groupId;
    }

    public final boolean isConnected() {
        return connected;
    }

    public String getGroupId() {
        return groupId;
    }
}
