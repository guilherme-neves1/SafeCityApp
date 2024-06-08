package com.example.safecityapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class HomeActivity extends NavMenuActivity {

    private boolean isLiked1 = false;
    private boolean isLiked2 = false;
    private boolean isLiked3 = false;
    private boolean isLiked4 = false;
    private boolean isLiked5 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_home, findViewById(R.id.fragment_container));

        // Botões de navegação
        Button btnReport = findViewById(R.id.btnReport);
        Button btnBulletin = findViewById(R.id.btnBulletin);
        Button btnMap = findViewById(R.id.btnMap);
        Button btnHelp = findViewById(R.id.btnHelp);

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, PostActivity.class));
            }
        });

        btnBulletin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ReportActivity.class));
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MapsActivity.class));
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, HelpActivity.class));
            }
        });

        // Interações dos botões das postagens
        setupPostButtonInteractions();
    }

    private void setupPostButtonInteractions() {
        // Postagem 1
        ImageButton btnLike1 = findViewById(R.id.btnLike1);
        ImageButton btnComment1 = findViewById(R.id.btnComment1);
        ImageButton btnShare1 = findViewById(R.id.btnShare1);

        btnLike1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLike(v, 1);
            }
        });
        btnComment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonColor(v);
            }
        });
        btnShare1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonColor(v);
                showToast("Post compartilhado!");
            }
        });

        // Postagem 2
        ImageButton btnLike2 = findViewById(R.id.btnLike2);
        ImageButton btnComment2 = findViewById(R.id.btnComment2);
        ImageButton btnShare2 = findViewById(R.id.btnShare2);

        btnLike2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLike(v, 2);
            }
        });
        btnComment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonColor(v);
            }
        });
        btnShare2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonColor(v);
                showToast("Post compartilhado!");
            }
        });

        // Postagem 3
        ImageButton btnLike3 = findViewById(R.id.btnLike3);
        ImageButton btnComment3 = findViewById(R.id.btnComment3);
        ImageButton btnShare3 = findViewById(R.id.btnShare3);

        btnLike3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLike(v, 3);
            }
        });
        btnComment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonColor(v);
            }
        });
        btnShare3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonColor(v);
                showToast("Post compartilhado!");
            }
        });

        // Postagem 4
        ImageButton btnLike4 = findViewById(R.id.btnLike4);
        ImageButton btnComment4 = findViewById(R.id.btnComment4);
        ImageButton btnShare4 = findViewById(R.id.btnShare4);

        btnLike4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLike(v, 4);
            }
        });
        btnComment4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonColor(v);
            }
        });
        btnShare4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonColor(v);
                showToast("Post compartilhado!");
            }
        });

        // Postagem 5
        ImageButton btnLike5 = findViewById(R.id.btnLike5);
        ImageButton btnComment5 = findViewById(R.id.btnComment5);
        ImageButton btnShare5 = findViewById(R.id.btnShare5);

        btnLike5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLike(v, 5);
            }
        });
        btnComment5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonColor(v);
            }
        });
        btnShare5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonColor(v);
                showToast("Post compartilhado!");
            }
        });
    }

    private void toggleLike(View v, int postNumber) {
        boolean isLiked = false;
        switch (postNumber) {
            case 1: isLiked = isLiked1; isLiked1 = !isLiked1; break;
            case 2: isLiked = isLiked2; isLiked2 = !isLiked2; break;
            case 3: isLiked = isLiked3; isLiked3 = !isLiked3; break;
            case 4: isLiked = isLiked4; isLiked4 = !isLiked4; break;
            case 5: isLiked = isLiked5; isLiked5 = !isLiked5; break;
        }

        ImageButton btnLike = (ImageButton) v;
        if (isLiked) {
            btnLike.setImageResource(R.drawable.home_favoriteoff);
            btnLike.setColorFilter(Color.BLACK);
            showToast("Postagem descurtida!");
        } else {
            btnLike.setImageResource(R.drawable.home_favoriteon);
            btnLike.setColorFilter(Color.parseColor("#E2725B"));
            showToast("Postagem curtida!");
        }
    }

    private void toggleButtonColor(View v) {
        ImageButton button = (ImageButton) v;
        int color = ((ColorDrawable) button.getBackground()).getColor();
        if (color == Color.BLACK) {
            button.setColorFilter(Color.parseColor("#E2725B"));
        } else {
            button.setColorFilter(Color.BLACK);
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
