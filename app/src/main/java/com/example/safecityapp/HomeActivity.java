package com.example.safecityapp;

import android.os.Bundle;

public class HomeActivity extends NavMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_home, findViewById(R.id.fragment_container));
    }
}
