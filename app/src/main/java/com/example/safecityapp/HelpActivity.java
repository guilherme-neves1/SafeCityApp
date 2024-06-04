package com.example.safecityapp;

import android.os.Bundle;

public class HelpActivity extends NavMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_help, findViewById(R.id.fragment_container));
    }
}
