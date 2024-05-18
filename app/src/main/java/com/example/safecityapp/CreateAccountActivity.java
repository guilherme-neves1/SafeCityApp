package com.example.safecityapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.safecityapp.service.CepResponse;
import com.example.safecityapp.service.RetrofitClient;
import com.example.safecityapp.service.ViaCepService;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccountActivity extends AppCompatActivity {

    EditText fullNameInput, birthInput, cepInput,
            addressInput, numInput, complInput,
            distInput, cityInput, telInput,
            emailInput, passwordInput,
            confirmPasswordInput;

    Spinner stateSpinner, sexSpinner;

    Button registerBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Vinculando os campos de entrada
        fullNameInput = findViewById(R.id.fullNameInput);
        birthInput = findViewById(R.id.birthInput);
        sexSpinner = findViewById(R.id.sexSpinner);

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
                R.array.states_brazil, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);

        // Configurando o Spinner de sexo
        ArrayAdapter<CharSequence> sexAdapter = ArrayAdapter.createFromResource(this,
                R.array.sex_options, R.layout.spinner_item);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(sexAdapter);

        // Configurando o campo de data de nascimento com um DatePicker
        birthInput.setOnClickListener(v -> showDatePickerDialog());

        // Configurando preenchimento automático do CEP
        cepInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 8) {
                    // Buscar dados do endereço pelo CEP usando a API
                    fetchAddress(s.toString());
                }
            }
        });

        registerBtn.setOnClickListener(v -> {
            String fullName = fullNameInput.getText().toString();
            String birth = birthInput.getText().toString();
            String sex = sexSpinner.getSelectedItem().toString();

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

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> birthInput.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1),
                year, month, day);
        datePickerDialog.show();
    }

    private void fetchAddress(String cep) {
        ViaCepService service = RetrofitClient.getRetrofitInstance().create(ViaCepService.class);
        Call<CepResponse> call = service.getCepInfo(cep);

        call.enqueue(new Callback<CepResponse>() {
            @Override
            public void onResponse(Call<CepResponse> call, Response<CepResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    addressInput.setText(response.body().getLogradouro());
                    distInput.setText(response.body().getBairro());
                    cityInput.setText(response.body().getLocalidade());
                    // Aqui você pode querer preencher o Spinner de estado
                    String uf = response.body().getUf();
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(CreateAccountActivity.this,
                            R.array.states_brazil, R.layout.spinner_item);
                    stateSpinner.setAdapter(adapter);
                    if (uf != null) {
                        int spinnerPosition = adapter.getPosition(uf);
                        stateSpinner.setSelection(spinnerPosition);
                    }
                } else {
                    Toast.makeText(CreateAccountActivity.this, "CEP não encontrado.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CepResponse> call, Throwable t) {
                Toast.makeText(CreateAccountActivity.this, "Erro ao buscar CEP.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
