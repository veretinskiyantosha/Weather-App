package com.anton.veretinsky.weatherapp.data;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.anton.veretinsky.weatherapp.data.source.local.dao.CurrentWeatherDao;
import com.anton.veretinsky.weatherapp.data.source.local.entity.CurrentWeatherEntity;
import com.anton.veretinsky.weatherapp.data.source.prefs.UserPreferences;
import com.anton.veretinsky.weatherapp.data.source.remote.WeatherApi;
import com.anton.veretinsky.weatherapp.data.source.remote.response.CurrentWeatherForSeveralCitiesResponse;
import com.anton.veretinsky.weatherapp.data.source.remote.response.CurrentWeatherResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class CurrentWeatherRepository {
    private final CurrentWeatherDao currentWeatherDao;
    private final WeatherApi weatherApi;
    private final NetworkReachability reachability;
    private final UserPreferences preferences;

    private final String[] favouriteCityIds = new String[]{
            "2643743",
            "2968815",
            "1850147",
            "5128638"
    };

    @Inject
    public CurrentWeatherRepository(
            CurrentWeatherDao currentWeatherDao,
            WeatherApi weatherApi,
            NetworkReachability reachability,
            UserPreferences preferences
    ) {
        this.currentWeatherDao = currentWeatherDao;
        this.weatherApi = weatherApi;
        this.reachability = reachability;
        this.preferences = preferences;
    }

    public LiveData<Resource<List<CurrentWeatherEntity>>> loadCurrentWeatherForFavouriteCities() {
        return new NetworkBoundResource<List<CurrentWeatherEntity>, CurrentWeatherForSeveralCitiesResponse>() {

            @Override
            protected void saveCallResult(@NonNull CurrentWeatherForSeveralCitiesResponse item) {
                currentWeatherDao.insertList(CurrentWeatherUtils.toEntityList(item.list));
            }

            @Override
            protected boolean shouldFetch(@Nullable List<CurrentWeatherEntity> data) {
                return reachability.isConnected();
            }

            @NonNull
            @Override
            protected LiveData<List<CurrentWeatherEntity>> loadFromDb() {
                return currentWeatherDao.getCurrentWeatherByCityIds(favouriteCityIds);
            }

            @NonNull
            @Override
            protected Single<CurrentWeatherForSeveralCitiesResponse> createCall() {
                return weatherApi.getCurrentWeatherByCityIds(TextUtils.join(",", favouriteCityIds));
            }
        }.asLiveData();
    }

    public LiveData<Resource<CurrentWeatherEntity>> loadCurrentWeatherByGeoCoords(double lat, double lon) {
        return new NetworkBoundResource<CurrentWeatherEntity, CurrentWeatherResponse>() {

            @Override
            protected void saveCallResult(@NonNull CurrentWeatherResponse item) {
                preferences.setCityId(item.id);
                currentWeatherDao.insert(CurrentWeatherUtils.toEntity(item));
            }

            @Override
            protected boolean shouldFetch(@Nullable CurrentWeatherEntity data) {
                return reachability.isConnected();
            }

            @NonNull
            @Override
            protected LiveData<CurrentWeatherEntity> loadFromDb() {
                return currentWeatherDao.getCurrentWeatherByCityId(preferences.getCityId());
            }

            @NonNull
            @Override
            protected Single<CurrentWeatherResponse> createCall() {
                return weatherApi.getCurrentWeatherByGeoCoords(lat, lon);
            }
        }.asLiveData();
    }

    public LiveData<Resource<CurrentWeatherEntity>> loadCurrentWeatherByCityName(String query) {
        return new NetworkBoundResource<CurrentWeatherEntity, CurrentWeatherResponse>() {

            @Override
            protected void saveCallResult(@NonNull CurrentWeatherResponse item) {
                currentWeatherDao.insert(CurrentWeatherUtils.toEntity(item));
            }

            @Override
            protected boolean shouldFetch(@Nullable CurrentWeatherEntity data) {
                return reachability.isConnected();
            }

            @NonNull
            @Override
            protected LiveData<CurrentWeatherEntity> loadFromDb() {
                return currentWeatherDao.searchCurrentWeatherByCityName(query);
            }

            @NonNull
            @Override
            protected Single<CurrentWeatherResponse> createCall() {
                return weatherApi.getCurrentWeatherByCityName(query);
            }
        }.asLiveData();
    }
}
