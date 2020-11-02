package com.anton.veretinsky.weatherapp.ui.addcity.recycler;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anton.veretinsky.weatherapp.R;
import com.anton.veretinsky.weatherapp.data.source.local.entity.CurrentWeatherEntity;
import com.anton.veretinsky.weatherapp.ui.StringUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrentWeatherHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.city_text_view)
    TextView cityTextView;
    @BindView(R.id.temp_text_view)
    TextView tempTextView;
    @BindView(R.id.weather_text_view)
    TextView weatherTextView;
    @BindView(R.id.last_update_text_view)
    TextView lastUpdateTextView;

    public CurrentWeatherHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindCurrentWeather(CurrentWeatherEntity currentWeather) {
        final Context context = itemView.getContext();

        cityTextView.setText(currentWeather.city);
        tempTextView.setText(context.getString(R.string.temp_in_cels, currentWeather.temp));
        weatherTextView.setText(StringUtils.capitalize(currentWeather.weather));

        long now = new Date().getTime();
        long time = currentWeather.dt * 1000;

        CharSequence lastUpdate = DateUtils.getRelativeTimeSpanString(
                time,
                now,
                DateUtils.MINUTE_IN_MILLIS
        );

        lastUpdateTextView.setText(lastUpdate);
    }
}
