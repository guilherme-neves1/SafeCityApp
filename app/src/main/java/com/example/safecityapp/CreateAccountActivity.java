package com.example.safecityapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreateAccountActivity extends AppCompatActivity {

    EditText fullNameInput, birthInput, cepInput, sexInput,
            addressInput, numInput, complInput,
            distInput, cityInput, telInput,
            emailInput, passwordInput,
            confirmPasswordInput;

    Spinner stateSpinner;

    Button registerBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Vinculando os campos de entrada
        fullNameInput = findViewById(R.id.fullNameInput);
        birthInput = findViewById(R.id.birthInput);
        sexInput = findViewById(R.id.sexInput);

        cepInput = findViewById(R.id.cepInput);
        addressInput = findViewById(R.id.addressInput);
        numInput = findViewById(R.id.numInput);
        complInput = findViewById(R.id.complInput);
        distInput = findViewById(R.id.distInput);
        cityInput = findViewById(R.id.cityInput);
        stateSpinner = findViewById(R.id.stateSpinner);

        telInput = findViewById(R.id.telInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);

        registerBtn = findViewById(R.id.registerBtn);

        // Configurando o Spinner de estados
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.states_brazil, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);

        registerBtn.setOnClickListener(v -> {
            String fullName = fullNameInput.getText().toString();
            String birth = birthInput.getText().toString();
            String sex = sexInput.getText().toString();

            String cep = cepInput.getText().toString();
            String address = addressInput.getText().toString();
            String num = numInput.getText().toString();
            String complement = complInput.getText().toString();
            String district = distInput.getText().toString();
            String city = cityInput.getText().toString();
            String state = stateSpinner.getSelectedItem().toString();

            String tel = telInput.getText().toString();
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            String confirmPassword = confirmPasswordInput.getText().toString();

            if (password.equals(confirmPassword)) {
                // Lógica para registrar o usuário
                Toast.makeText(CreateAccountActivity.this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();
                // Pode adicionar aqui a lógica para salvar os dados do usuário ou enviar para o servidor
            } else {
                Toast.makeText(CreateAccountActivity.this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
