package com.anton.veretinsky.weatherapp.data.source.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeatherResponse {
    public Coord coord;
    public List<Weather> weather;
    public Main main;
    public int id;
    public String name;
    public long dt;

    public static class Coord {
        public double lon;
        public double lat;
    }

    public static class Weather {
        public String description;
        public String icon;
    }

    public static class Main {
        public double temp;
        @SerializedName("temp_min")
        public double tempMin;
        @SerializedName("temp_max")
        public double tempMax;
    }
}
