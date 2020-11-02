package com.anton.veretinsky.weatherapp.ui.weatherforecast;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anton.veretinsky.weatherapp.R;
import com.anton.veretinsky.weatherapp.data.source.local.entity.CurrentWeatherEntity;
import com.anton.veretinsky.weatherapp.data.source.remote.Constants;
import com.anton.veretinsky.weatherapp.ui.ActivityUtils;
import com.anton.veretinsky.weatherapp.ui.StringUtils;
import com.anton.veretinsky.weatherapp.ui.weatherforecast.recycler.WeatherForecastAdapter;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;

import java.util.Collections;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public class WeatherForecastFragment extends DaggerFragment {
    private static final String TAG = WeatherForecastFragment.class.getName();

    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.temp_text_view)
    TextView tempTextView;
    @BindView(R.id.weather_text_view)
    TextView weatherTextView;
    @BindView(R.id.temp_min_text_view)
    TextView tempMinTextView;
    @BindView(R.id.temp_max_text_view)
    TextView tempMaxTextView;
    @BindView(R.id.weather_image_view)
    ImageView weatherImageView;
    @BindView(R.id.last_update_text_view)
    TextView lastUpdateTextView;
    @BindView(R.id.weather_forecast_recycler_view)
    RecyclerView weatherForecastRecyclerView;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private NavController navController;
    private WeatherForecastViewModel viewModel;

    private WeatherForecastAdapter adapter;

    @Nullable
    private CurrentWeatherEntity currentWeather = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (getArguments() != null) {
            currentWeather = WeatherForecastFragmentArgs.fromBundle(getArguments()).getCurrentWeather();
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_weather_forecast, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(WeatherForecastViewModel.class);

        setupAppbar();
        setupToolbar();
        initAdapter();
        updateCurrentWeather();

        if (currentWeather != null) {
            viewModel.loadWeatherForecastByCityId(currentWeather.id).observe(
                    getViewLifecycleOwner(),
                    weatherForecastEntityResource -> {
                        if (weatherForecastEntityResource.data != null) {
                            adapter.list = weatherForecastEntityResource.data.list;
                            adapter.notifyDataSetChanged();
                            updateTime(weatherForecastEntityResource.data.dt * 1000);
                        }
                    });
        }
    }

    private void setupToolbar() {
        ViewGroup.MarginLayoutParams toolbarLayoutParams = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
        toolbarLayoutParams.topMargin = ActivityUtils.getStatusBarHeight(requireContext());

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_ios_white);
        toolbar.setNavigationOnClickListener(view -> navController.popBackStack());

        toolbarTitle.setText(currentWeather != null ? currentWeather.city : getString(R.string.no_data));
    }

    private void setupAppbar() {
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) ->
                appbar.setSelected(v.canScrollVertically(-1)));
    }

    private void initAdapter() {
        adapter = new WeatherForecastAdapter();
        adapter.list = Collections.emptyList();

        weatherForecastRecyclerView.setAdapter(adapter);
        weatherForecastRecyclerView.setLayoutManager(new LinearLayoutManager(
                requireContext(),
                RecyclerView.HORIZONTAL,
                false
        ));
    }

    @SuppressLint("DefaultLocale")
    private void updateCurrentWeather() {
        tempTextView.setText(currentWeather != null
                ? getString(R.string.temp_in_cels, currentWeather.temp)
                : getString(R.string.no_data));
        weatherTextView.setText(currentWeather != null
                ? StringUtils.capitalize(currentWeather.weather)
                : getString(R.string.no_data));
        tempMinTextView.setText(currentWeather != null
                ? String.format("%.0f", currentWeather.tempMin)
                : getString(R.string.no_data));
        tempMaxTextView.setText(currentWeather != null
                ? getString(R.string.temp_in_cels, currentWeather.tempMax)
                : getString(R.string.no_data));
        if (currentWeather != null) {
            Glide.with(this)
                    .load(Constants.IMAGE_URL + currentWeather.icon + Constants.EXT_PNG)
                    .into(weatherImageView);
        }
    }

    private void updateTime(long time) {
        long now = new Date().getTime();
        CharSequence lastUpdate = DateUtils.getRelativeTimeSpanString(
                time,
                now,
                DateUtils.MINUTE_IN_MILLIS
        );
        lastUpdateTextView.setText(lastUpdate);
    }
}
