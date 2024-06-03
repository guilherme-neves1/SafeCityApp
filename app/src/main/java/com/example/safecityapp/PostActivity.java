package com.example.safecityapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class PostActivity extends AppCompatActivity {

    private EditText editTextPost;
    private ImageView cameraIcon, galleryIcon, deleteIcon, anonymousIcon;
    private Button submitPostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post); // Corrigido aqui

        editTextPost = findViewById(R.id.editTextPost);
        cameraIcon = findViewById(R.id.cameraIcon);
        galleryIcon = findViewById(R.id.galleryIcon);
        deleteIcon = findViewById(R.id.deleteIcon);
        anonymousIcon = findViewById(R.id.anonymousIcon);
        submitPostButton = findViewById(R.id.submitPostButton);

        // Adicionar funcionalidades aos ícones e botão
        cameraIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementar funcionalidade de câmera
            }
        });

        galleryIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementar funcionalidade de galeria
            }
        });

        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementar funcionalidade de deletar
            }
        });

        anonymousIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementar funcionalidade de postar anonimamente
            }
        });

        submitPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementar funcionalidade de enviar postagem
            }
        });
    }
}
