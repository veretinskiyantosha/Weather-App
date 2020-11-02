package com.anton.veretinsky.weatherapp.data.source.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.anton.veretinsky.weatherapp.data.source.local.dao.CurrentWeatherDao;
import com.anton.veretinsky.weatherapp.data.source.local.dao.WeatherForecastDao;
import com.anton.veretinsky.weatherapp.data.source.local.entity.CurrentWeatherEntity;
import com.anton.veretinsky.weatherapp.data.source.local.entity.WeatherForecastEntity;

@Database(
        entities = {
                CurrentWeatherEntity.class,
                WeatherForecastEntity.class
        },
        version = 1,
        exportSchema = false
)
public abstract class WeatherDatabase extends RoomDatabase {

    public abstract CurrentWeatherDao getCurrentWeatherDao();

    public abstract WeatherForecastDao getWeatherForecastDao();
}
