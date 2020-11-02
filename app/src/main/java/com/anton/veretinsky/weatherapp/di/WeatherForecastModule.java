package com.anton.veretinsky.weatherapp.di;

import androidx.lifecycle.ViewModel;

import com.anton.veretinsky.weatherapp.ui.weatherforecast.WeatherForecastFragment;
import com.anton.veretinsky.weatherapp.ui.weatherforecast.WeatherForecastViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

@Module
abstract class WeatherForecastModule {

    @ContributesAndroidInjector(modules = {ViewModelBuilder.class})
    abstract WeatherForecastFragment contributeWeatherForecastFragment();

    @Binds
    @IntoMap
    @ViewModelKey(WeatherForecastViewModel.class)
    abstract ViewModel bindViewModel(WeatherForecastViewModel viewModel);
}
