package com.anton.veretinsky.weatherapp.di;

import androidx.lifecycle.ViewModelProvider;

import com.anton.veretinsky.weatherapp.ui.ViewModelFactory;

import dagger.Binds;
import dagger.Module;

@Module
abstract class ViewModelBuilder {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
