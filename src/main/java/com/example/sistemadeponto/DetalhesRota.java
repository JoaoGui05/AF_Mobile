package com.example.sistemadeponto;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;
import java.util.Locale;

public class DetalhesRota extends AppCompatActivity {

    private Button btnFinalizar, btnAlertaVerde, btnAlertaAmarelo, btnAlertaVermelho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalhes_rota);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnAlertaVerde = findViewById(R.id.btnAlertaVerde);
        btnAlertaAmarelo = findViewById(R.id.btnAlertaAmarelo);
        btnAlertaVermelho = findViewById(R.id.btnAlertaVermelho);
        btnFinalizar = findViewById(R.id.btnFinalizar);

        btnAlertaVerde.setOnClickListener(v -> enviarAlerta("verde"));
        btnAlertaAmarelo.setOnClickListener(v -> enviarAlerta("amarelo"));
        btnAlertaVermelho.setOnClickListener(v -> enviarAlerta("vermelho"));

        // Finalizar rota
        btnFinalizar.setOnClickListener(v -> {
            Toast.makeText(this, "Rota finalizada com sucesso!", Toast.LENGTH_SHORT).show();
            finish(); // ou voltar para a tela de rotas
        });
    }

    private void enviarAlerta(String nivel) {
        String titulo = "Alerta de Ronda";
        String mensagem;
        switch (nivel) {
            case "verde":
                mensagem = "Anomalia detectada no ponto. Verificação recomendada.";
                break;
            case "amarelo":
                mensagem = "Atividade suspeita no ponto. Atenção requerida.";
                break;
            case "vermelho":
                mensagem = "Alerta vermelho! Sinistro detectado no ponto. Ajuda imediata necessária.";
                break;
            default:
                mensagem = "Alerta desconhecido.";
        }

        String data = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());

        // Envia notificação
        NotificacaoHelper.enviarNotificacao(this, titulo, mensagem);

        // Registra no banco de dados
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.registrarNotificacao(titulo, mensagem, data);

        // Toast de confirmação
        Toast.makeText(this, "Alerta " + nivel.toUpperCase() + " enviado ao supervisor!", Toast.LENGTH_SHORT).show();

        // Simula broadcast de alerta (opcional)
        Intent intent = new Intent("com.example.sistemadeponto.ALERTA_ENVIADO");
        intent.putExtra("nivel_alerta", nivel);
        sendBroadcast(intent);
    }
}