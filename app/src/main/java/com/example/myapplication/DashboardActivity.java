package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dashboard);

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);

            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

            NavigationUI.setupWithNavController(bottomNavigationView, navController);

            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.home_fragment, R.id.new_post_fragment, R.id.profile_fragment
            ).build();

            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        }
}