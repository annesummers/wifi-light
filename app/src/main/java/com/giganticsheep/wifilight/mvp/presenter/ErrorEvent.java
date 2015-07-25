package com.giganticsheep.wifilight.mvp.presenter;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 25/07/15. <p>
 * (*_*)
 */
public class ErrorEvent {
    private final Throwable error;

    public ErrorEvent(Throwable error) {
        this.error = error;
    }

    public Throwable getError() {
        return error;
    }
}
