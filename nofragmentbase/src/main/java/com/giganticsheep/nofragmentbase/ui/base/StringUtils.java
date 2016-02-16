package com.giganticsheep.nofragmentbase.ui.base;

/**
 * Created by anne on 15/02/16.
 */
public class StringUtils {
    public static boolean isNull(String string) {
        return string == null || string.contentEquals("") || string.contentEquals("null");
    }
}
