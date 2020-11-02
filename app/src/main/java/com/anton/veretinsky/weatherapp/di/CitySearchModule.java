package com.anton.veretinsky.weatherapp.di;

import androidx.lifecycle.ViewModel;

import com.anton.veretinsky.weatherapp.ui.citysearch.CitySearchFragment;
import com.anton.veretinsky.weatherapp.ui.citysearch.CitySearchViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

@Module
abstract class CitySearchModule {

    @ContributesAndroidInjector(modules = {ViewModelBuilder.class})
    abstract CitySearchFragment contributeCitySearchFragment();

    @Binds
    @IntoMap
    @ViewModelKey(CitySearchViewModel.class)
    abstract ViewModel bindViewModel(CitySearchViewModel viewModel);
}
