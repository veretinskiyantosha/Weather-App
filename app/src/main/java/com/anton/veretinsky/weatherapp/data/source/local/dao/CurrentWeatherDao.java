package com.anton.veretinsky.weatherapp.data.source.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.anton.veretinsky.weatherapp.data.source.local.entity.CurrentWeatherEntity;

import java.util.List;

@Dao
public interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CurrentWeatherEntity item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<CurrentWeatherEntity> list);

    @Query("SELECT * FROM CurrentWeatherEntity WHERE id = :id")
    LiveData<CurrentWeatherEntity> getCurrentWeatherByCityId(int id);

    @Query("SELECT * FROM CurrentWeatherEntity WHERE city LIKE :query LIMIT 1")
    LiveData<CurrentWeatherEntity> searchCurrentWeatherByCityName(String query);

    @Query("SELECT * FROM CurrentWeatherEntity WHERE id IN (:ids)")
    LiveData<List<CurrentWeatherEntity>> getCurrentWeatherByCityIds(String[] ids);
}
