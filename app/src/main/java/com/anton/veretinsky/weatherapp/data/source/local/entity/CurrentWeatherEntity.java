package com.anton.veretinsky.weatherapp.data.source.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class CurrentWeatherEntity implements Serializable {
    @PrimaryKey
    public int id;
    public String city;
    public double lon;
    public double lat;
    public String weather;
    public String icon;
    public double temp;
    public double tempMin;
    public double tempMax;
    public long dt;
}
