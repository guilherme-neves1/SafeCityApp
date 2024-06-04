package com.example.safecityapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.io.InputStream;

public class PostActivity extends NavMenuActivity {

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;
    private static final int REQUEST_LOCATION = 3;

    private EditText editTextPost;
    private ImageView cameraIcon, galleryIcon, deleteIcon, anonymousIcon, locatonIcon;
    private Button submitPostButton;
    private boolean isAnonymous = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_post, findViewById(R.id.fragment_container)); // MENU

        editTextPost = findViewById(R.id.editTextPost);
        cameraIcon = findViewById(R.id.cameraIcon);
        galleryIcon = findViewById(R.id.galleryIcon);
        deleteIcon = findViewById(R.id.deleteIcon);
        anonymousIcon = findViewById(R.id.anonymousIcon);
        locatonIcon = findViewById(R.id.locatonIcon);
        submitPostButton = findViewById(R.id.submitPostButton);

        cameraIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(PostActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PostActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                } else {
                    openCamera();
                }
            }
        });

        galleryIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(PostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PostActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_GALLERY);
                } else {
                    openGallery();
                }
            }
        });

        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextPost.setText("");
                Toast.makeText(PostActivity.this, "Mensagem deletada!", Toast.LENGTH_SHORT).show(); // Mensagem de deletar
            }
        });

        anonymousIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAnonymous = !isAnonymous;
                if (isAnonymous) {
                    anonymousIcon.setImageResource(R.drawable.post_visibilityoff);
                    Toast.makeText(PostActivity.this, "Postagem anônima: Ativado", Toast.LENGTH_SHORT).show();
                } else {
                    anonymousIcon.setImageResource(R.drawable.post_visibilityon);
                    Toast.makeText(PostActivity.this, "Postagem anônima: Desativado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        locatonIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(PostActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PostActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                } else {
                    showLocationPermissionGrantedMessage();
                }
            }
        });

        submitPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextPost.setText("");
                Toast.makeText(PostActivity.this, "Postagem feita!", Toast.LENGTH_SHORT).show(); // Mensagem de postagem feita
            }
        });
    }

    private void showLocationPermissionGrantedMessage() {
        Toast.makeText(this, "Permissão de localização concedida", Toast.LENGTH_SHORT).show();
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA && data != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                addImageToEditText(photo);
            } else if (requestCode == REQUEST_GALLERY && data != null) {
                Uri selectedImage = data.getData();
                try {
                    InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                    Bitmap photo = BitmapFactory.decodeStream(imageStream);
                    addImageToEditText(photo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addImageToEditText(Bitmap bitmap) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(editTextPost.getText());
        ssb.append("\n");

        ImageSpan imageSpan = new ImageSpan(this, bitmap);
        ssb.setSpan(imageSpan, ssb.length() - 1, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        editTextPost.setText(ssb);
        editTextPost.setSelection(ssb.length());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showLocationPermissionGrantedMessage();
            } else {
                Toast.makeText(this, "Permissão de localização negada", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
