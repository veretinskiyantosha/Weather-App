package com.anton.veretinsky.weatherapp.ui;

import androidx.annotation.NonNull;

public class StringUtils {

    public static String capitalize(@NonNull String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
}
