package com.example.safecityapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.BreakIterator;
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
        // Adicione aqui o mapeamento dos estados
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 8) {
                    // Buscar dados do endereço pelo CEP usando a API
                    fetchAddress(s.toString());
                }
            }
        });

        registerBtn.setOnClickListener(v -> {
            if (areFieldsValid()) {
                // Lógica para registrar o usuário
//                Salvar();


                // Mostrar mensagem de sucesso
                Toast.makeText(CreateAccountActivity.this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();

                // Redirecionar para a tela de login após o registro bem-sucedido
                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finaliza a atividade atual para que o usuário não possa voltar para a tela de registro com o botão "Voltar"
            }
        });

        // Adicionando TextWatcher a todos os campos para limpar erros
        TextWatcher clearErrorWatcher = new ClearErrorTextWatcher();
        fullNameInput.addTextChangedListener(clearErrorWatcher);
        birthInput.addTextChangedListener(clearErrorWatcher);
        cepInput.addTextChangedListener(clearErrorWatcher);
        addressInput.addTextChangedListener(clearErrorWatcher);
        numInput.addTextChangedListener(clearErrorWatcher);
        complInput.addTextChangedListener(clearErrorWatcher);
        distInput.addTextChangedListener(clearErrorWatcher);
        cityInput.addTextChangedListener(clearErrorWatcher);
        telInput.addTextChangedListener(clearErrorWatcher);
        emailInput.addTextChangedListener(clearErrorWatcher);
        passwordInput.addTextChangedListener(clearErrorWatcher);
        confirmPasswordInput.addTextChangedListener(clearErrorWatcher);

        // Validação do formato de email
        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    emailInput.setError("Email inválido");
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
                    String uf = cepResponse.getUf();
                    if (ufMap.containsKey(uf)) {
                        int position = ((ArrayAdapter<String>) stateSpinner.getAdapter()).getPosition(ufMap.get(uf));
                        stateSpinner.setSelection(position);
                    }
                    addressInput.setEnabled(false);
                    distInput.setEnabled(false);
                    cityInput.setEnabled(false);
                    stateSpinner.setEnabled(false);
                } else {
                    clearAddressFields();
                    Toast.makeText(CreateAccountActivity.this, "CEP inválido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CepResponse> call, Throwable t) {
                clearAddressFields();
                Toast.makeText(CreateAccountActivity.this, "Erro ao buscar CEP", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearAddressFields() {
        addressInput.setText("");
        distInput.setText("");
        cityInput.setText("");
        stateSpinner.setSelection(0);
        addressInput.setEnabled(true);
        distInput.setEnabled(true);
        cityInput.setEnabled(true);
        stateSpinner.setEnabled(true);
    }

    private boolean areFieldsValid() {
        boolean valid = true;

        valid &= validateField(fullNameInput, "Nome completo é obrigatório");
        valid &= validateField(birthInput, "Data de nascimento é obrigatória");
        valid &= validateField(sexSpinner, "Sexo é obrigatório");
        valid &= validateField(cepInput, "CEP é obrigatório");
        valid &= validateField(addressInput, "Endereço é obrigatório");
        valid &= validateField(numInput, "Número é obrigatório");
        valid &= validateField(distInput, "Bairro é obrigatório");
        valid &= validateField(cityInput, "Cidade é obrigatória");
        valid &= validateField(telInput, "Telefone é obrigatório");
        valid &= validateField(emailInput, "Email é obrigatório");
        valid &= validateField(passwordInput, "Senha é obrigatória");
        valid &= validateField(confirmPasswordInput, "Confirmação de senha é obrigatória");

        // Validação do estado como opcional
        if (stateSpinner.getSelectedItemPosition() == 0 && stateSpinner.isEnabled()) {
            ((TextView) stateSpinner.getSelectedView()).setError(null);
            ((TextView) stateSpinner.getSelectedView()).setTextColor(Color.BLACK);
        }

        // Validação do formato de e-mail
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput.getText().toString()).matches()) {
            emailInput.setError("Email inválido");
            valid = false;
        }

        // Validação da senha
        if (!passwordInput.getText().toString().equals(confirmPasswordInput.getText().toString())) {
            confirmPasswordInput.setError("Senhas não correspondem");
            valid = false;
        }

        return valid;
    }

    private boolean validateField(EditText field, String errorMessage) {
        if (field.getText().toString().trim().isEmpty()) {
            field.setError(errorMessage);
            field.setTextColor(Color.RED);
            return false;
        } else {
            field.setTextColor(Color.BLACK);
        }
        return true;
    }

    private boolean validateField(Spinner spinner, String errorMessage) {
        if (spinner.getSelectedItemPosition() == 0) {
            ((TextView) spinner.getSelectedView()).setError(errorMessage);
            ((TextView) spinner.getSelectedView()).setTextColor(Color.RED);
            return false;
        } else {
            ((TextView) spinner.getSelectedView()).setTextColor(Color.BLACK);
        }
        return true;
    }

    private class ClearErrorTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            EditText editText = (EditText) getCurrentFocus();
            if (editText != null) {
                editText.setError(null);
                editText.setTextColor(Color.BLACK);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    }

//    public void Salvar() {
//        String msg = "";
//        String txtNome = nome.getText().toString();
//        String integerDtnascimento = dtnascimento.getText().toString();
//        String txtSexo = sexo.getText().toString();
//        String integerCep = cep.getText().toString();
//        String txtEndereco = endereco.getText().toString();
//        String integerNumero = numero.getText().toString();
//        String txtComplemento = complemento.getText().toString();
//        String txtBairro = bairro.getText().toString();
//        String txtCidade= cidade.getText().toString();
//        String txtEstado = estado.getText().toString();
//        String integerTelefone = telefone.getText().toString();
//        String txtEmail = email.getText().toString();
//        String txtSenha = senha.getText().toString();
//
//        BancoController bd = new BancoController(getBaseContext());
//        String resultado;
//
//        resultado = bd.insereDadosUsuarios(txtNome, integerDtnascimento, txtSexo, integerCep, txtEndereco, integerNumero, txtComplemento, txtBairro, txtCidade, txtEstado, integerTelefone, txtEmail, txtSenha);
//
//        Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
//        limpar();
//        }
//
//    }
}


