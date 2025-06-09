package com.example.sistemadeponto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SelecaoLogin extends AppCompatActivity {

    Button btnLoginFuncionario, btnLoginGerente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_selecao_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnLoginFuncionario = findViewById(R.id.btnLoginFuncionario);
        btnLoginGerente = findViewById(R.id.btnLoginGerente);

       btnLoginFuncionario.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelecaoLogin.this, LoginFuncionario.class); // temporário
                startActivity(intent);
            }
        });

        btnLoginGerente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Substituir com a tela de login do gerente
                Intent intent = new Intent(SelecaoLogin.this, LoginGerente.class);
                startActivity(intent);
            }
        });

    }
}