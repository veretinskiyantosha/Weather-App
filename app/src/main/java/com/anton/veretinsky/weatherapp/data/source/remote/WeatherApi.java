package com.anton.veretinsky.weatherapp.data.source.remote;

import com.anton.veretinsky.weatherapp.data.source.remote.response.CurrentWeatherForSeveralCitiesResponse;
import com.anton.veretinsky.weatherapp.data.source.remote.response.CurrentWeatherResponse;
import com.anton.veretinsky.weatherapp.data.source.remote.response.WeatherForecastResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("weather")
    Single<CurrentWeatherResponse> getCurrentWeatherByGeoCoords(
            @Query("lat") Double lat,
            @Query("lon") Double lon
    );

    @GET("weather")
    Single<CurrentWeatherResponse> getCurrentWeatherByCityName(
            @Query("q") String query
    );

    @GET("group")
    Single<CurrentWeatherForSeveralCitiesResponse> getCurrentWeatherByCityIds(
            @Query(value = "id", encoded = true) String ids
    );

    @GET("forecast")
    Single<WeatherForecastResponse> getWeatherForecastByCityId(
            @Query("id") int cityId,
            @Query("cnt") int count
    );
}
