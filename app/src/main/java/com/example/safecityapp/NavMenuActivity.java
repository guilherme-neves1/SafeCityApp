package com.example.safecityapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;


public class NavMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_mapa) {
            Intent mapIntent = new Intent(this, MapsActivity.class);
            startActivity(mapIntent);
        } else if (id == R.id.nav_post) {
            Intent postIntent = new Intent(this, PostActivity.class);
            startActivity(postIntent);
        } else if (id == R.id.nav_home) {
            Intent homeIntent = new Intent(this, HomeActivity.class);
            startActivity(homeIntent);
        } else if (id == R.id.nav_police) {
            Intent reportIntent = new Intent(this, ReportActivity.class);
            startActivity(reportIntent);
        } else if (id == R.id.nav_settings) {
            //Intent settingsIntent = new Intent(this, SettingsActivity.class);
            //startActivity(settingsIntent);
        } else if (id == R.id.nav_help) {
            Intent helpIntent = new Intent(this, HelpActivity.class);
            startActivity(helpIntent);
        } else if (id == R.id.nav_about) {
            //Intent aboutIntent = new Intent(this, AboutActivity.class);
            //startActivity(aboutIntent);
        } else if (id == R.id.nav_logout) {
            // Implementar a navegação para logout
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
