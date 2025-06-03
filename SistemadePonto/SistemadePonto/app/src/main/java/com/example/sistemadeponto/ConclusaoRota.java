package com.example.sistemadeponto;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;
import java.util.Locale;

public class ConclusaoRota extends AppCompatActivity {

    private Button btnVoltarInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conclusao_rota);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnVoltarInicio = findViewById(R.id.btnVoltarInicio);

        btnVoltarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Funcionario funcionario = FuncionarioStorage.getFuncionarioLogado();
                DatabaseHelper dbHelper = new DatabaseHelper(ConclusaoRota.this);

                // 1. Atualiza status para online
                dbHelper.inserirStatus(funcionario.getNome(), "online");

                // 2. Cria notificação de conclusão da ronda
                String titulo = "Ronda finalizada";
                String mensagem = "O funcionário " + funcionario.getNome() + " concluiu a ronda com sucesso.";
                String data = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());


                // 3. Salva no banco e envia push local
                dbHelper.registrarNotificacao(titulo, mensagem, data);
                NotificacaoHelper.enviarNotificacao(ConclusaoRota.this, titulo, mensagem);

                // 4. Redireciona para tela inicial
                Intent intent = new Intent(ConclusaoRota.this, MainActivity2.class);
                intent.putExtra("fragment_to_load", "inicio");
                startActivity(intent);
                finish();
            }
        });
    }}