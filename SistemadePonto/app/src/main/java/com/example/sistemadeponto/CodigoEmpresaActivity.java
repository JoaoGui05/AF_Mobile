package com.example.sistemadeponto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CodigoEmpresaActivity extends AppCompatActivity {

    Button btnLogin;
    EditText inputCodigoEmpresa;
    private final String CODIGO_CORRETO = "1234SGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_codigo_empresa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        inputCodigoEmpresa = findViewById(R.id.inputCodigoEmpresa);
        btnLogin = findViewById(R.id.buttonLogin);

        btnLogin.setOnClickListener(v -> {
            String codigoDigitado = inputCodigoEmpresa.getText().toString();
            if (codigoDigitado.equals(CODIGO_CORRETO)) {
                startActivity(new Intent(this, SelecaoLogin.class));
                finish();
            } else {
                Toast.makeText(this, "Código incorreto!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}