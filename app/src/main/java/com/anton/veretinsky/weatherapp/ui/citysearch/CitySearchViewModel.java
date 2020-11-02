package com.anton.veretinsky.weatherapp.ui.citysearch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.anton.veretinsky.weatherapp.data.CurrentWeatherRepository;
import com.anton.veretinsky.weatherapp.data.Resource;
import com.anton.veretinsky.weatherapp.data.source.local.entity.CurrentWeatherEntity;

import javax.inject.Inject;

public class CitySearchViewModel extends ViewModel {
    private final CurrentWeatherRepository currentWeatherRepository;

    @Inject
    public CitySearchViewModel(CurrentWeatherRepository currentWeatherRepository) {
        this.currentWeatherRepository = currentWeatherRepository;
    }

    public LiveData<Resource<CurrentWeatherEntity>> searchCurrentWeatherByCityName(String query) {
        return currentWeatherRepository.loadCurrentWeatherByCityName(query);
    }
}
