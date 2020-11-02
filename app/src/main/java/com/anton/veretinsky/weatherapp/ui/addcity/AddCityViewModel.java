package com.anton.veretinsky.weatherapp.ui.addcity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.anton.veretinsky.weatherapp.data.CurrentWeatherRepository;
import com.anton.veretinsky.weatherapp.data.Resource;
import com.anton.veretinsky.weatherapp.data.source.local.entity.CurrentWeatherEntity;

import java.util.List;

import javax.inject.Inject;

public class AddCityViewModel extends ViewModel {
    private final CurrentWeatherRepository currentWeatherRepository;

    @Inject
    public AddCityViewModel(CurrentWeatherRepository currentWeatherRepository) {
        this.currentWeatherRepository = currentWeatherRepository;
    }

    public LiveData<Resource<List<CurrentWeatherEntity>>> loadCurrentWeatherForFavouriteCities() {
        return currentWeatherRepository.loadCurrentWeatherForFavouriteCities();
    }

    public LiveData<Resource<CurrentWeatherEntity>> loadCurrentWeatherByGeoCoords(double lat, double lon) {
        return currentWeatherRepository.loadCurrentWeatherByGeoCoords(lat, lon);
    }
}
