package com.anton.veretinsky.weatherapp.di;

import android.content.Context;

import androidx.room.Room;

import com.anton.veretinsky.weatherapp.BuildConfig;
import com.anton.veretinsky.weatherapp.data.source.local.WeatherDatabase;
import com.anton.veretinsky.weatherapp.data.source.local.dao.CurrentWeatherDao;
import com.anton.veretinsky.weatherapp.data.source.local.dao.WeatherForecastDao;
import com.anton.veretinsky.weatherapp.data.source.remote.AddParamsInterceptor;
import com.anton.veretinsky.weatherapp.data.source.remote.ApiKeyInterceptor;
import com.anton.veretinsky.weatherapp.data.source.remote.WeatherApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.anton.veretinsky.weatherapp.data.source.remote.Constants.BASE_URL;

@Module
public class AppModule {

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new ApiKeyInterceptor())
                .addInterceptor(new AddParamsInterceptor());

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

        return builder.build();
    }

    @Singleton
    @Provides
    public WeatherApi provideWeatherApi(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(WeatherApi.class);
    }

    @Singleton
    @Provides
    public WeatherDatabase provideWeatherDatabase(Context context) {
        return Room.databaseBuilder(context, WeatherDatabase.class, "WeatherApp.db").build();
    }

    @Singleton
    @Provides
    public CurrentWeatherDao provideCurrentWeatherDao(WeatherDatabase database) {
        return database.getCurrentWeatherDao();
    }

    @Singleton
    @Provides
    public WeatherForecastDao provideWeatherForecastDao(WeatherDatabase database) {
        return database.getWeatherForecastDao();
    }
}
