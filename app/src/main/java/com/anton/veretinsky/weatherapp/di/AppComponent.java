package com.anton.veretinsky.weatherapp.di;

import android.content.Context;

import com.anton.veretinsky.weatherapp.App;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                AppModule.class,
                AddCityModule.class,
                CitySearchModule.class,
                WeatherForecastModule.class
        }
)
public interface AppComponent extends AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        AppComponent create(@BindsInstance Context context);
    }
}
