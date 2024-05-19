package com.example.safecityapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import android.graphics.Color;
import android.content.res.ColorStateList;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.safecityapp.service.CepResponse;
import com.example.safecityapp.service.RetrofitClient;
import com.example.safecityapp.service.ViaCepService;

import java.util.Calendar;
import java.util.HashMap;


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

    private HashMap<String, String> ufMap;

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

        // Inicializando o mapeamento UF -> Nome completo
        ufMap = new HashMap<>();
        ufMap.put("AC", "Acre");
        ufMap.put("AL", "Alagoas");
        ufMap.put("AP", "Amapá");
        ufMap.put("AM", "Amazonas");
        ufMap.put("BA", "Bahia");
        ufMap.put("CE", "Ceará");
        ufMap.put("DF", "Distrito Federal");
        ufMap.put("ES", "Espírito Santo");
        ufMap.put("GO", "Goiás");
        ufMap.put("MA", "Maranhão");
        ufMap.put("MT", "Mato Grosso");
        ufMap.put("MS", "Mato Grosso do Sul");
        ufMap.put("MG", "Minas Gerais");
        ufMap.put("PA", "Pará");
        ufMap.put("PB", "Paraíba");
        ufMap.put("PR", "Paraná");
        ufMap.put("PE", "Pernambuco");
        ufMap.put("PI", "Piauí");
        ufMap.put("RJ", "Rio de Janeiro");
        ufMap.put("RN", "Rio Grande do Norte");
        ufMap.put("RS", "Rio Grande do Sul");
        ufMap.put("RO", "Rondônia");
        ufMap.put("RR", "Roraima");
        ufMap.put("SC", "Santa Catarina");
        ufMap.put("SP", "São Paulo");
        ufMap.put("SE", "Sergipe");
        ufMap.put("TO", "Tocantins");

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

            // Verificar se todos os campos obrigatórios estão preenchidos
            if (fullName.isEmpty() || birth.isEmpty() || sex.isEmpty() || cep.isEmpty() || address.isEmpty() || district.isEmpty()  || city.isEmpty() || state.isEmpty() || tel.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                // Destacar os campos não preenchidos
                highlightEmptyFields();
                Toast.makeText(CreateAccountActivity.this, "Todos os campos com * devem ser preenchidos.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Verificar se a senha tem pelo menos 6 caracteres
            if (password.length() < 6) {
                Toast.makeText(CreateAccountActivity.this, "A senha deve ter pelo menos 6 caracteres.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.equals(confirmPassword)) {
                // Lógica para registrar o usuário
                Toast.makeText(CreateAccountActivity.this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();
                // Pode adicionar aqui a lógica para salvar os dados do usuário ou enviar para o servidor
            } else {
                Toast.makeText(CreateAccountActivity.this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show();
            }
        });

        // Limpar o destaque vermelho quando o usuário começar a preencher um campo
        TextWatcher clearErrorWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                clearErrors();
            }
        };

        // Adicionando TextWatcher a todos os campos
        fullNameInput.addTextChangedListener(clearErrorWatcher);
        birthInput.addTextChangedListener(clearErrorWatcher);
        cepInput.addTextChangedListener(clearErrorWatcher);
        addressInput.addTextChangedListener(clearErrorWatcher);
        distInput.addTextChangedListener(clearErrorWatcher);
        cityInput.addTextChangedListener(clearErrorWatcher);
        telInput.addTextChangedListener(clearErrorWatcher);
        emailInput.addTextChangedListener(clearErrorWatcher);
        passwordInput.addTextChangedListener(clearErrorWatcher);
        confirmPasswordInput.addTextChangedListener(clearErrorWatcher);


        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().contains("@")) {
                    emailInput.setError("Email deve conter '@'");
                }
            }
        });


        // Formatação de Telefone
        telInput.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private final String phoneMask = "(##) #####-####";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    String cleanString = s.toString().replaceAll("[^\\d]", "");
                    StringBuilder formatted = new StringBuilder();
                    int i = 0;
                    for (char m : phoneMask.toCharArray()) {
                        if (m != '#' && i < cleanString.length()) {
                            formatted.append(m);
                        } else if (i < cleanString.length()) {
                            formatted.append(cleanString.charAt(i));
                            i++;
                        }
                    }
                    current = formatted.toString();
                    telInput.setText(current);
                    telInput.setSelection(current.length());
                }
            }
        });
    }


    // Data de Nascimento - Calendário
    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    String formattedDate = String.format("%02d/%02d/%04d", dayOfMonth, month1 + 1, year1);
                    birthInput.setText(formattedDate);
                },
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
                    CepResponse cepResponse = response.body();
                    addressInput.setText(cepResponse.getLogradouro());
                    distInput.setText(cepResponse.getBairro());
                    cityInput.setText(cepResponse.getLocalidade());

                    // Atualizando o Spinner de estado
                    String uf = cepResponse.getUf();
                    if (uf != null) {
                        String stateName = ufMap.get(uf);
                        if (stateName != null) {
                            ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) stateSpinner.getAdapter();
                            int spinnerPosition = adapter.getPosition(stateName);
                            if (spinnerPosition >= 0) {
                                stateSpinner.setSelection(spinnerPosition);
                            }
                        }
                    }

                    // Tornar os campos não editáveis
                    distInput.setEnabled(false);
                    cityInput.setEnabled(false);
                    stateSpinner.setEnabled(false);

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

    // Métodos para destacar campos vazios em vermelho e limpar erros
    private void highlightEmptyFields() {
        if (fullNameInput.getText().toString().isEmpty()) {
            TextInputLayout fullNameInputLayout = findViewById(R.id.fullNameInputLayout);
            fullNameInputLayout.setBoxStrokeColor(Color.RED);
            fullNameInputLayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
            fullNameInput.setError("Campo obrigatório");
        }
        if (birthInput.getText().toString().isEmpty()) {
            TextInputLayout birthInputLayout = findViewById(R.id.birthInputLayout);
            birthInputLayout.setBoxStrokeColor(Color.RED);
            birthInputLayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
            birthInput.setError("Campo obrigatório");
        }
        if (cepInput.getText().toString().isEmpty()) {
            TextInputLayout cepInputLayout = findViewById(R.id.cepInputLayout);
            cepInputLayout.setBoxStrokeColor(Color.RED);
            cepInputLayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
            cepInput.setError("Campo obrigatório");
        }
        if (addressInput.getText().toString().isEmpty()) {
            TextInputLayout addressInputLayout = findViewById(R.id.addressInputLayout);
            addressInputLayout.setBoxStrokeColor(Color.RED);
            addressInputLayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
            addressInput.setError("Campo obrigatório");
        }
        if (distInput.getText().toString().isEmpty()) {
            TextInputLayout distInputLayout = findViewById(R.id.distInputLayout);
            distInputLayout.setBoxStrokeColor(Color.RED);
            distInputLayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
            distInput.setError("Campo obrigatório");
        }
        if (cityInput.getText().toString().isEmpty()) {
            TextInputLayout cityInputLayout = findViewById(R.id.cityInputLayout);
            cityInputLayout.setBoxStrokeColor(Color.RED);
            cityInputLayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
            cityInput.setError("Campo obrigatório");
        }
        if (telInput.getText().toString().isEmpty()) {
            TextInputLayout telInputLayout = findViewById(R.id.telInputLayout);
            telInputLayout.setBoxStrokeColor(Color.RED);
            telInputLayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
            telInput.setError("Campo obrigatório");
        }
        if (emailInput.getText().toString().isEmpty()) {
            TextInputLayout emailInputLayout = findViewById(R.id.emailInputLayout);
            emailInputLayout.setBoxStrokeColor(Color.RED);
            emailInputLayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
            emailInput.setError("Campo obrigatório");
        }
        if (passwordInput.getText().toString().isEmpty()) {
            TextInputLayout passwordInputLayout = findViewById(R.id.passwordInputLayout);
            passwordInputLayout.setBoxStrokeColor(Color.RED);
            passwordInputLayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
            passwordInput.setError("Campo obrigatório");
        }
        if (confirmPasswordInput.getText().toString().isEmpty()) {
            TextInputLayout confirmPasswordInputLayout = findViewById(R.id.confirmPasswordInputLayout);
            confirmPasswordInputLayout.setBoxStrokeColor(Color.RED);
            confirmPasswordInputLayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
            confirmPasswordInput.setError("Campo obrigatório");
        }
    }

    private void clearErrors() {
        clearFieldError((TextInputEditText) fullNameInput, R.id.fullNameInputLayout);
        clearFieldError((TextInputEditText) birthInput, R.id.birthInputLayout);
        clearFieldError((TextInputEditText) cepInput, R.id.cepInputLayout);
        clearFieldError((TextInputEditText) addressInput, R.id.addressInputLayout);
        clearFieldError((TextInputEditText) distInput, R.id.distInputLayout);
        clearFieldError((TextInputEditText) cityInput, R.id.cityInputLayout);
        clearFieldError((TextInputEditText) telInput, R.id.telInputLayout);
        clearFieldError((TextInputEditText) emailInput, R.id.emailInputLayout);
        clearFieldError((TextInputEditText) passwordInput, R.id.passwordInputLayout);
        clearFieldError((TextInputEditText) confirmPasswordInput, R.id.confirmPasswordInputLayout);
    }

    private void clearFieldError(TextInputEditText field, int layoutId) {
        TextInputLayout fieldLayout = findViewById(layoutId);
        fieldLayout.setBoxStrokeColor(Color.parseColor("#404040"));
        fieldLayout.setHintTextColor(ColorStateList.valueOf(Color.BLACK));
        field.setError(null);
    }

}
