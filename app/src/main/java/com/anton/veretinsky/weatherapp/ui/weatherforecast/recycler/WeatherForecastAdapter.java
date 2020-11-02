package com.anton.veretinsky.weatherapp.ui.weatherforecast.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anton.veretinsky.weatherapp.R;

import java.util.List;

import static com.anton.veretinsky.weatherapp.data.source.remote.response.WeatherForecastResponse.WeatherForecast;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastHolder> {
    public List<WeatherForecast> list;

    @NonNull
    @Override
    public WeatherForecastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.weather_forecast_list_item, parent, false);
        return new WeatherForecastHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherForecastHolder holder, int position) {
        final WeatherForecast weatherForecast = list.get(position);
        holder.bindWeatherForecast(weatherForecast);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
