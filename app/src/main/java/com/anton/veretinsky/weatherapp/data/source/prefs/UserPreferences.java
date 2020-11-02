package com.anton.veretinsky.weatherapp.data.source.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserPreferences {
    private static final String USER_PREFERENCES_NAME = "user_preferences";
    private static final String CITY_ID_KEY = "city_id";

    private final SharedPreferences sharedPreferences;

    @Inject
    public UserPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(
                USER_PREFERENCES_NAME,
                Context.MODE_PRIVATE
        );
    }

    public synchronized int getCityId() {
        return sharedPreferences.getInt(CITY_ID_KEY, -1);
    }

    public synchronized void setCityId(int id) {
        sharedPreferences.edit().putInt(CITY_ID_KEY, id).apply();
    }
}
