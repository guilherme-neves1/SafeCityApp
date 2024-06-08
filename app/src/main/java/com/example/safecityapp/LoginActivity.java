package com.example.safecityapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn, createAccBtn;
    EditText loginEmailInput, loginPasswordInput;
    TextInputLayout loginEmailInputLayout, loginPasswordInputLayout;
    TextView forgotPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmailInput = findViewById(R.id.loginEmailInput);
        loginPasswordInput = findViewById(R.id.loginPasswordInput);

        loginEmailInputLayout = findViewById(R.id.loginEmailInputLayout);
        loginPasswordInputLayout = findViewById(R.id.loginPasswordInputLayout);

        loginBtn = findViewById(R.id.loginBtn);
        createAccBtn = findViewById(R.id.createAccBtn);
        forgotPasswordText = findViewById(R.id.forgotPasswordText);

        loginEmailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = s.toString();
                if (!email.contains("@")) {
                    loginEmailInputLayout.setError("Email deve conter '@'");
                } else {
                    loginEmailInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        loginPasswordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = s.toString();
                if (password.length() < 6) {
                    loginPasswordInputLayout.setError("Senha deve conter no mínimo 6 caracteres");
                } else {
                    loginPasswordInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmailInput.getText().toString();
                String password = loginPasswordInput.getText().toString();

                if (validateEmail(email) && validatePassword(password)) {
                    BancoController bancoController = new BancoController(LoginActivity.this);
                    if (bancoController.validarLogin(email, password)) {
                        Log.i("Login Success", "Email: " + email + " and Password: " + password);
                        Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                    } else {
                        loginEmailInputLayout.setError("Email ou senha inválidos");
                        loginPasswordInputLayout.setError("Email ou senha inválidos");
                    }
                } else {
                    if (!validateEmail(email)) {
                        loginEmailInputLayout.setError("Email inválido");
                    }
                    if (!validatePassword(password)) {
                        loginPasswordInputLayout.setError("Senha inválida");
                    }
                }
            }
        });

        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccountIntent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(createAccountIntent);
            }
        });
    }

    private boolean validateEmail(String email) {
        return email.contains("@") && email.length() > 5;
    }

    private boolean validatePassword(String password) {
        return password.length() >= 6;
    }
}
