package com.anton.veretinsky.weatherapp.ui.weatherforecast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.anton.veretinsky.weatherapp.data.Resource;
import com.anton.veretinsky.weatherapp.data.WeatherForecastRepository;
import com.anton.veretinsky.weatherapp.data.source.local.entity.WeatherForecastEntity;

import javax.inject.Inject;

public class WeatherForecastViewModel extends ViewModel {
    private final WeatherForecastRepository weatherForecastRepository;

    @Inject
    public WeatherForecastViewModel(WeatherForecastRepository weatherForecastRepository) {
        this.weatherForecastRepository = weatherForecastRepository;
    }

    public LiveData<Resource<WeatherForecastEntity>> loadWeatherForecastByCityId(int id) {
        return weatherForecastRepository.loadWeatherForecastByCityId(id);
    }
}
