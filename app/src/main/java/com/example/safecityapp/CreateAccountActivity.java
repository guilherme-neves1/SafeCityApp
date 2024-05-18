package com.example.safecityapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreateAccountActivity extends AppCompatActivity {

    EditText fullNameInput, birthInput, cepInput, sexInput,
            addressInput, numInput, complementoInput,
            bairroInput, cidadeInput, estadoInput,
            telefoneInput, emailInput, passwordInput,
            confirmPasswordInput;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Vinculando os campos de entrada
        fullNameInput = findViewById(R.id.fullNameInput);
        birthInput = findViewById(R.id.birthInput);
        cepInput = findViewById(R.id.cepInput);
        sexInput = findViewById(R.id.sexInput);
        addressInput = findViewById(R.id.addressInput);
        numInput = findViewById(R.id.numInput);
        complementoInput = findViewById(R.id.complementoInput);
        bairroInput = findViewById(R.id.bairroInput);
        cidadeInput = findViewById(R.id.cidadeInput);
        estadoInput = findViewById(R.id.estadoInput);
        telefoneInput = findViewById(R.id.telefoneInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(v -> {
            String fullName = fullNameInput.getText().toString();
            String dob = dobInput.getText().toString();
            String cep = cepInput.getText().toString();
            String sexo = sexoInput.getText().toString();
            String endereco = enderecoInput.getText().toString();
            String numero = numeroInput.getText().toString();
            String complemento = complementoInput.getText().toString();
            String bairro = bairroInput.getText().toString();
            String cidade = cidadeInput.getText().toString();
            String estado = estadoInput.getText().toString();
            String telefone = telefoneInput.getText().toString();
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
