package com.example.safecityapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

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
            if (fullName.isEmpty() || birth.isEmpty() || cep.isEmpty() || address.isEmpty() || city.isEmpty() || tel.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
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

//                    // Mudar a cor do texto para #A8A8A8
//                    distInput.setTextColor(Color.parseColor("#A8A8A8"));
//                    cityInput.setTextColor(Color.parseColor("#A8A8A8"));
//
//                    // Para mudar a cor do texto no Spinner, precisamos customizar o Spinner
//                    ((TextView) stateSpinner.getSelectedView()).setTextColor(Color.parseColor("#A8A8A8"));


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
