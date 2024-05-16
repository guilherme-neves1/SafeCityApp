package com.example.safecityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    Button loginBtn, createAccBtn;
    EditText emailInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        createAccBtn = (Button) findViewById(R.id.createAccBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                Log.i("Test Credentials", "Email: " + email + " and Password: " + password);
            }
        });

        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaMapa();
            }
        });
    }

    private void irParaMapa() {
        Intent googleMaps = new Intent(this, MapsActivity.class);
        startActivity(googleMaps);
    }

}