package com.anton.veretinsky.weatherapp.ui.weatherforecast.recycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anton.veretinsky.weatherapp.R;
import com.anton.veretinsky.weatherapp.data.source.remote.Constants;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.anton.veretinsky.weatherapp.data.source.remote.response.WeatherForecastResponse.WeatherForecast;

public class WeatherForecastHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.time_text_view)
    TextView timeTextView;
    @BindView(R.id.weather_image_view)
    ImageView weatherImageView;
    @BindView(R.id.temp_text_view)
    TextView tempTextView;

    private final SimpleDateFormat dateFormat;

    @SuppressLint("SimpleDateFormat")
    public WeatherForecastHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        dateFormat = new SimpleDateFormat("HH:mm");
    }

    public void bindWeatherForecast(WeatherForecast weatherForecast) {
        final Context context = itemView.getContext();
        final String time = dateFormat.format(new Date(weatherForecast.dt * 1000));
        timeTextView.setText(time);
        Glide.with(context)
                .load(Constants.IMAGE_URL + weatherForecast.weather.get(0).icon + Constants.EXT_PNG)
                .into(weatherImageView);
        tempTextView.setText(context.getString(R.string.temp_in_cels, weatherForecast.main.temp));
    }
}
