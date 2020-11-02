package com.anton.veretinsky.weatherapp.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.anton.veretinsky.weatherapp.data.source.local.dao.WeatherForecastDao;
import com.anton.veretinsky.weatherapp.data.source.local.entity.WeatherForecastEntity;
import com.anton.veretinsky.weatherapp.data.source.remote.WeatherApi;
import com.anton.veretinsky.weatherapp.data.source.remote.response.WeatherForecastResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class WeatherForecastRepository {
    private static final int NUMBER_OF_TIMESTAMPS = 8;

    private final WeatherForecastDao weatherForecastDao;
    private final WeatherApi weatherApi;
    private final NetworkReachability reachability;

    @Inject
    public WeatherForecastRepository(
            WeatherForecastDao weatherForecastDao,
            WeatherApi weatherApi,
            NetworkReachability reachability
    ) {
        this.weatherForecastDao = weatherForecastDao;
        this.weatherApi = weatherApi;
        this.reachability = reachability;
    }

    public LiveData<Resource<WeatherForecastEntity>> loadWeatherForecastByCityId(int id) {
        return new NetworkBoundResource<WeatherForecastEntity, WeatherForecastResponse>() {
            @Override
            protected void saveCallResult(@NonNull WeatherForecastResponse item) {
                weatherForecastDao.insertWithDt(WeatherForecastUtils.toEntity(item));
            }

            @Override
            protected boolean shouldFetch(@Nullable WeatherForecastEntity data) {
                return reachability.isConnected();
            }

            @NonNull
            @Override
            protected LiveData<WeatherForecastEntity> loadFromDb() {
                return weatherForecastDao.getWeatherForecastById(id);
            }

            @NonNull
            @Override
            protected Single<WeatherForecastResponse> createCall() {
                return weatherApi.getWeatherForecastByCityId(id, NUMBER_OF_TIMESTAMPS);
            }
        }.asLiveData();
    }
}
