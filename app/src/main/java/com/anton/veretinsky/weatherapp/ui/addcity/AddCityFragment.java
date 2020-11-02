package com.anton.veretinsky.weatherapp.ui.addcity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anton.veretinsky.weatherapp.BuildConfig;
import com.anton.veretinsky.weatherapp.R;
import com.anton.veretinsky.weatherapp.data.source.local.entity.CurrentWeatherEntity;
import com.anton.veretinsky.weatherapp.ui.ActivityUtils;
import com.anton.veretinsky.weatherapp.ui.StringUtils;
import com.anton.veretinsky.weatherapp.ui.addcity.recycler.CurrentWeatherAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public class AddCityFragment extends DaggerFragment {
    private static final int PERMISSION_REQUEST_CODE = 34;

    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.my_city_current_weather_layout)
    ConstraintLayout currentWeatherLayout;
    @BindView(R.id.city_text_view)
    TextView cityTextView;
    @BindView(R.id.temp_text_view)
    TextView tempTextView;
    @BindView(R.id.weather_text_view)
    TextView weatherTextView;
    @BindView(R.id.last_update_text_view)
    TextView lastUpdateTextView;
    @BindView(R.id.current_weather_recycler_view)
    RecyclerView currentWeatherRecyclerView;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private AddCityViewModel viewModel;
    private NavController navController;

    private FusedLocationProviderClient client;
    private LocationRequest request;
    private LocationCallback callback;

    private CurrentWeatherAdapter adapter;

    @Nullable
    private CurrentWeatherEntity currentWeather = null;
    private boolean isLocating = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        client = LocationServices.getFusedLocationProviderClient(requireContext());
        request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);
        callback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult.getLastLocation() != null) {
                    Location location = locationResult.getLastLocation();
                    viewModel.loadCurrentWeatherByGeoCoords(
                            location.getLatitude(),
                            location.getLongitude()
                    ).observe(getViewLifecycleOwner(), currentWeatherEntityResource -> {
                        isLocating = false;
                        currentWeather = currentWeatherEntityResource.data;

                        updateCurrentWeather();
                    });
                } else {
                    isLocating = false;

                    updateCurrentWeather();
                }
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_add_city, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(AddCityViewModel.class);

        setupToolbar();
        setupAppbar();
        initAdapter();
        initClickListener();

        viewModel.loadCurrentWeatherForFavouriteCities().observe(getViewLifecycleOwner(), listResource -> {
            if (listResource.data != null) {
                adapter.list = listResource.data;
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!isLocating && currentWeather == null) {
            isLocating = true;

            if (permissionApproved()) {
                requestLocationUpdates();
            } else {
                requestPermission();
            }
        }

        updateCurrentWeather();
    }

    @Override
    public void onDestroy() {
        client.removeLocationUpdates(callback);
        super.onDestroy();
    }

    private boolean permissionApproved() {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
        );
    }

    private void requestPermission() {
        requestPermissions(
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_REQUEST_CODE
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocationUpdates();
            } else {
                isLocating = false;

                updateCurrentWeather();

                Snackbar snackbar = Snackbar.make(
                        requireView(),
                        R.string.permission_requirement_explanation,
                        Snackbar.LENGTH_LONG
                );
                snackbar.setAction(R.string.settings, view -> {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts(
                            "package",
                            BuildConfig.APPLICATION_ID,
                            null
                    );
                    intent.setData(uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });
                snackbar.show();
            }
        }
    }

    private void setupToolbar() {
        ViewGroup.MarginLayoutParams toolbarLayoutParams = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
        toolbarLayoutParams.topMargin = ActivityUtils.getStatusBarHeight(requireContext());

        toolbar.inflateMenu(R.menu.menu_add_city);
        Menu menu = toolbar.getMenu();

        MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setQueryHint(getString(R.string.city_search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ActivityUtils.hideKeyboard(requireActivity());

                AddCityFragmentDirections.ActionAddCityFragmentToCitySearchFragment action =
                        AddCityFragmentDirections.actionAddCityFragmentToCitySearchFragment();
                action.setQuery(query);

                navController.navigate(action);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setupAppbar() {
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) ->
                appbar.setSelected(v.canScrollVertically(-1)));
    }

    private void initAdapter() {
        adapter = new CurrentWeatherAdapter();
        adapter.list = Collections.emptyList();
        adapter.listener = currentWeather -> {
            AddCityFragmentDirections.ActionAddCityFragmentToWeatherForecastFragment action =
                    AddCityFragmentDirections.actionAddCityFragmentToWeatherForecastFragment(currentWeather);
            navController.navigate(action);
        };

        currentWeatherRecyclerView.setAdapter(adapter);
        currentWeatherRecyclerView.setLayoutManager(new LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
        ));
    }

    private void initClickListener() {
        currentWeatherLayout.setOnClickListener(view -> {
            if (currentWeather != null) {
                AddCityFragmentDirections.ActionAddCityFragmentToWeatherForecastFragment action =
                        AddCityFragmentDirections.actionAddCityFragmentToWeatherForecastFragment(currentWeather);
                navController.navigate(action);
            }
        });
    }

    private void updateCurrentWeather() {
        if (!isLocating) {
            cityTextView.setText(currentWeather != null
                    ? currentWeather.city
                    : getString(R.string.no_data));
            tempTextView.setText(currentWeather != null
                    ? getString(R.string.temp_in_cels, currentWeather.temp)
                    : getString(R.string.no_data));
            weatherTextView.setText(currentWeather != null
                    ? StringUtils.capitalize(currentWeather.weather)
                    : getString(R.string.no_data));

            if (currentWeather != null) {
                long now = new Date().getTime();
                long time = currentWeather.dt * 1000;

                CharSequence lastUpdate = DateUtils.getRelativeTimeSpanString(
                        time,
                        now,
                        DateUtils.MINUTE_IN_MILLIS
                );

                lastUpdateTextView.setText(lastUpdate);
            } else {
                lastUpdateTextView.setText("");
            }
        } else {
            cityTextView.setText(R.string.locating);
            tempTextView.setText(R.string.no_data);
            weatherTextView.setText(R.string.no_data);
        }

    }

    @SuppressLint("MissingPermission")
    private void requestLocationUpdates() {
        client.requestLocationUpdates(request, callback, Looper.myLooper());
    }
}
