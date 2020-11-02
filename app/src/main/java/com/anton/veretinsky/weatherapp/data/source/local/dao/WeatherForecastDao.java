package com.anton.veretinsky.weatherapp.data.source.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.anton.veretinsky.weatherapp.data.source.local.entity.WeatherForecastEntity;

@Dao
public abstract class WeatherForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(WeatherForecastEntity item);

    public void insertWithDt(WeatherForecastEntity item) {
        item.dt = System.currentTimeMillis() / 1000;
        insert(item);
    }

    @Query("SELECT * FROM WeatherForecastEntity WHERE id = :id")
    public abstract LiveData<WeatherForecastEntity> getWeatherForecastById(int id);
}
