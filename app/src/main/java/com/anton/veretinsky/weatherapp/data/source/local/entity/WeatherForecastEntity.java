package com.anton.veretinsky.weatherapp.data.source.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import static com.anton.veretinsky.weatherapp.data.source.remote.response.WeatherForecastResponse.WeatherForecast;

@Entity
public class WeatherForecastEntity {
    @PrimaryKey
    public int id;
    public String city;
    public double lat;
    public double lon;
    @TypeConverters(WeatherForecastTypeConverter.class)
    public List<WeatherForecast> list;
    public long dt;

    public static class WeatherForecastTypeConverter {

        @TypeConverter
        public String listToJson(List<WeatherForecast> list) {
            return new Gson().toJson(list);
        }

        @TypeConverter
        public List<WeatherForecast> jsonToList(String json) {
            if (json == null || json.isEmpty()) {
                return null;
            }

            Type type = new TypeToken<List<WeatherForecast>>() {
            }.getType();

            return new Gson().fromJson(json, type);
        }
    }
}
