package com.example.sistemadeponto;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFuncionario extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private EditText editUsuario, editSenha;
    private Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_funcionario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
      //  btnEntrar = findViewById(R.id.btnEntrar);

        public void cadastrarUsuario(View v) {
            editUsuario = findViewById(R.id.editUsuario);
            editSenha = findViewById(R.id.editSenha);
            mAuth.createUserWithEmailAndPassword(editUsuario.getText().toString(), editSenha.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Usuário criado com sucesso", Toast.LENGTH_LONG).show();
                            Log.d("FIREBASE", "Usuário criado com sucesso");
                        } else {
                            Toast.makeText(this, "Erro ao criar usuário: " + task.getException(), Toast.LENGTH_LONG).show();
                            Log.e("FIREBASE", "Erro ao criar usuário", task.getException());
                        }
                    });
            }

    public void logarUsuario(View v) {
        EditText editUsuario = findViewById(R.id.editUsuario);
        EditText editSenha = findViewById(R.id.editSenha);
        mAuth.signInWithEmailAndPassword(editUsuario.getText().toString(), editSenha.getText().toString())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Login bem-sucedido", Toast.LENGTH_LONG).show();
                        Log.d("FIREBASE", "Login bem-sucedido");
                        Intent intent = new Intent(LoginFuncionario.this, MainActivity2.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Erro no login: " + task.getException(), Toast.LENGTH_LONG).show();
                        Log.e("FIREBASE", "Erro no login", task.getException());
                    }
                });
    }

    /*    TextWatcher loginWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String usuarioInput = editUsuario.getText().toString().trim();
                String senhaInput = editSenha.getText().toString().trim();
                btnEntrar.setEnabled(!usuarioInput.isEmpty() && !senhaInput.isEmpty());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };

        editUsuario.addTextChangedListener(loginWatcher);
        editSenha.addTextChangedListener(loginWatcher);

        btnEntrar.setOnClickListener(v -> {
            String usuarioInput = editUsuario.getText().toString().trim();
            String senhaInput = editSenha.getText().toString().trim();

            boolean loginValido = false;
            for (Funcionario f : FuncionarioStorage.getListaFuncionarios()) {
                if (f.getNome().equals(usuarioInput) && f.getSenha().equals(senhaInput)) {
                    FuncionarioStorage.setFuncionarioLogado(f);
                    loginValido = true;
                    break;
                }
            }

            if (loginValido) {
                Toast.makeText(LoginFuncionario.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginFuncionario.this, MainActivity2.class); // ou a tela desejada
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginFuncionario.this, "Usuário ou senha incorretos.", Toast.LENGTH_SHORT).show();
            }
        }); */
    }
