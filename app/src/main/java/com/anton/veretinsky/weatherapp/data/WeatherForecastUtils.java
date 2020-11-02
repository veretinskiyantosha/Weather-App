package com.anton.veretinsky.weatherapp.data;

import com.anton.veretinsky.weatherapp.data.source.local.entity.WeatherForecastEntity;
import com.anton.veretinsky.weatherapp.data.source.remote.response.WeatherForecastResponse;

public class WeatherForecastUtils {

    public static WeatherForecastEntity toEntity(WeatherForecastResponse response) {
        WeatherForecastEntity entity = new WeatherForecastEntity();
        entity.id = response.city.id;
        entity.city = response.city.name;
        entity.lat = response.city.coord.lat;
        entity.lon = response.city.coord.lon;
        entity.list = response.list;
        return entity;
    }
}
