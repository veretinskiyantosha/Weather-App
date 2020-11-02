package com.anton.veretinsky.weatherapp.ui.addcity.recycler;

import com.anton.veretinsky.weatherapp.data.source.local.entity.CurrentWeatherEntity;

public interface CurrentWeatherListener {
    void onCurrentWeatherClick(CurrentWeatherEntity currentWeather);
}