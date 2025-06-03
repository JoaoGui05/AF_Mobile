package com.example.sistemadeponto;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginGerente extends AppCompatActivity {

    private EditText editUsuario2, editSenha2;
    private Button btnEntrar2;
    private final String EMAIL_CORRETO = "admin@empresa.com";
    private final String SENHA_CORRETA = "seguranca123";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_gerente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editUsuario2 = findViewById(R.id.editUsuario2);
        editSenha2 = findViewById(R.id.editSenha2);
        btnEntrar2 = findViewById(R.id.btnEntrar2);

        TextWatcher loginWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String usuarioInput = editUsuario2.getText().toString().trim();
                String senhaInput = editSenha2.getText().toString().trim();
                btnEntrar2.setEnabled(!usuarioInput.isEmpty() && !senhaInput.isEmpty());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };

        editUsuario2.addTextChangedListener(loginWatcher);
        editSenha2.addTextChangedListener(loginWatcher);

        btnEntrar2.setOnClickListener(v -> {
            String email = editUsuario2.getText().toString().trim();
            String senha = editSenha2.getText().toString().trim();

            if (email.equals(EMAIL_CORRETO) && senha.equals(SENHA_CORRETA)) {
                Intent intent = new Intent(LoginGerente.this, MainActivity3.class); // ou MainGerenteActivity
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginGerente.this, "Credenciais inválidas!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}