package com.anton.veretinsky.weatherapp.ui.citysearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anton.veretinsky.weatherapp.R;
import com.anton.veretinsky.weatherapp.ui.ActivityUtils;
import com.anton.veretinsky.weatherapp.ui.addcity.recycler.CurrentWeatherAdapter;
import com.google.android.material.appbar.AppBarLayout;

import java.util.Collections;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public class CitySearchFragment extends DaggerFragment {

    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.current_weather_recycler_view)
    RecyclerView currentWeatherRecyclerView;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private CitySearchViewModel viewModel;
    private NavController navController;

    private CurrentWeatherAdapter adapter;
    private String query;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (getArguments() != null) {
            query = CitySearchFragmentArgs.fromBundle(getArguments()).getQuery();
        } else {
            query = "";
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_search_city, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(CitySearchViewModel.class);

        setupToolbar();
        setupAppbar();
        iniAdapter();

        viewModel.searchCurrentWeatherByCityName(query).observe(getViewLifecycleOwner(), currentWeatherEntityResource -> {
            if (currentWeatherEntityResource.data != null) {
                adapter.list = Collections.singletonList(currentWeatherEntityResource.data);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setupToolbar() {
        ViewGroup.MarginLayoutParams toolbarLayoutParams = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
        toolbarLayoutParams.topMargin = ActivityUtils.getStatusBarHeight(requireContext());

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_ios_white);
        toolbar.setNavigationOnClickListener(view -> navController.popBackStack());

        toolbarTitle.setText(R.string.search_result);
    }

    private void setupAppbar() {
        currentWeatherRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                appbar.setSelected(recyclerView.canScrollVertically(-1));
            }
        });
    }

    private void iniAdapter() {
        adapter = new CurrentWeatherAdapter();
        adapter.list = Collections.emptyList();
        adapter.listener = currentWeather -> {
            CitySearchFragmentDirections.ActionCitySearchFragmentToWeatherForecastFragment action =
                    CitySearchFragmentDirections.actionCitySearchFragmentToWeatherForecastFragment(currentWeather);
            navController.navigate(action);
        };

        currentWeatherRecyclerView.setAdapter(adapter);
        currentWeatherRecyclerView.setLayoutManager(new LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
        ));
    }
}
