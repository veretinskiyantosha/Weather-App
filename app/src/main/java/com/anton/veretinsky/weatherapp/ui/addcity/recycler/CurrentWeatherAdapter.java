package com.anton.veretinsky.weatherapp.ui.addcity.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anton.veretinsky.weatherapp.R;
import com.anton.veretinsky.weatherapp.data.source.local.entity.CurrentWeatherEntity;

import java.util.List;

public class CurrentWeatherAdapter extends RecyclerView.Adapter<CurrentWeatherHolder> {
    public List<CurrentWeatherEntity> list;
    public CurrentWeatherListener listener;

    @NonNull
    @Override
    public CurrentWeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.current_weather_list_item, parent, false);
        return new CurrentWeatherHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentWeatherHolder holder, int position) {
        final CurrentWeatherEntity currentWeather = list.get(position);
        holder.bindCurrentWeather(currentWeather);
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onCurrentWeatherClick(currentWeather);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}