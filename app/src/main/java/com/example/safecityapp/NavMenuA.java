package com.example.safecityapp;

import android.annotation.SuppressLint;
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
import com.example.safecityapp.R;


public class NavMenuA extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
            // Implementar a navegação para a tela de post
        } else if (id == R.id.nav_home) {
            // Implementar a navegação para a tela home
        } else if (id == R.id.nav_police) {
            // Implementar a navegação para a tela de boletim
        } else if (id == R.id.nav_settings) {
            // Implementar a navegação para a tela de configurações
        } else if (id == R.id.nav_help) {
            // Implementar a navegação para a tela de ajuda
        } else if (id == R.id.nav_about) {
            // Implementar a navegação para a tela sobre
        } else if (id == R.id.nav_logout) {
            // Implementar a navegação para logout
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}