package com.anton.veretinsky.weatherapp.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import com.anton.veretinsky.weatherapp.R;
import com.anton.veretinsky.weatherapp.ui.ActivityUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityUtils.setStatusBarOverlay(getWindow());
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(
                this,
                R.id.nav_host_fragment
        ).navigateUp() || super.onSupportNavigateUp();
    }
}