package com.anton.veretinsky.weatherapp.data.source.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherForecastResponse {
    public List<WeatherForecast> list;
    public City city;

    public static class WeatherForecast {
        public long dt;
        public Main main;
        public List<Weather> weather;

        public static class Main {
            public double temp;
            @SerializedName("temp_min")
            public double tempMin;
            @SerializedName("temp_max")
            public double tempMax;
        }

        public static class Weather {
            public String description;
            public String icon;
        }
    }

    public static class City {
        public int id;
        public String name;
        public Coord coord;

        public static class Coord {
            public double lat;
            public double lon;
        }
    }
}
