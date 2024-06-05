package com.example.safecityapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class AboutActivity extends NavMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_about, findViewById(R.id.fragment_container)); // MENU


    }
}
