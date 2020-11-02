package com.anton.veretinsky.weatherapp.di;

import androidx.lifecycle.ViewModel;

import com.anton.veretinsky.weatherapp.ui.addcity.AddCityFragment;
import com.anton.veretinsky.weatherapp.ui.addcity.AddCityViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

@Module
abstract class AddCityModule {

    @ContributesAndroidInjector(modules = {ViewModelBuilder.class})
    abstract AddCityFragment contributeAddCityFragment();

    @Binds
    @IntoMap
    @ViewModelKey(AddCityViewModel.class)
    abstract ViewModel bindViewModel(AddCityViewModel viewModel);
}
