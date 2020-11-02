package com.anton.veretinsky.weatherapp.data;

import com.anton.veretinsky.weatherapp.data.source.local.entity.CurrentWeatherEntity;
import com.anton.veretinsky.weatherapp.data.source.remote.response.CurrentWeatherResponse;

import java.util.ArrayList;
import java.util.List;

public class CurrentWeatherUtils {

    public static List<CurrentWeatherEntity> toEntityList(List<CurrentWeatherResponse> list) {
        List<CurrentWeatherEntity> entityList = new ArrayList<>(list.size());
        for (CurrentWeatherResponse response : list) {
            entityList.add(toEntity(response));
        }
        return entityList;
    }

    public static CurrentWeatherEntity toEntity(CurrentWeatherResponse response) {
        CurrentWeatherEntity entity = new CurrentWeatherEntity();
        entity.id = response.id;
        entity.city = response.name;
        entity.lon = response.coord.lon;
        entity.lat = response.coord.lat;
        entity.weather = response.weather.get(0).description;
        entity.icon = response.weather.get(0).icon;
        entity.temp = response.main.temp;
        entity.tempMin = response.main.tempMin;
        entity.tempMax = response.main.tempMax;
        entity.dt = response.dt;
        return entity;
    }
}
