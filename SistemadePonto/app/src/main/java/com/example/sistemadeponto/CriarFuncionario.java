package com.example.sistemadeponto;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class CriarFuncionario extends AppCompatActivity {

    EditText edtNome, edtSenha, edtHoraEntrada, edtHoraSaida;
    Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_criar_funcionario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtNome = findViewById(R.id.edtNomeFuncionario);
        edtSenha = findViewById(R.id.edtSenhaFuncionario);
        edtHoraEntrada = findViewById(R.id.edtHoraEntrada);
        edtHoraSaida = findViewById(R.id.edtHoraSaida);
        btnSalvar = findViewById(R.id.btnSalvarFuncionario);

        // Abre time picker ao clicar nos horários
        edtHoraEntrada.setOnClickListener(v -> abrirTimePicker(edtHoraEntrada));
        edtHoraSaida.setOnClickListener(v -> abrirTimePicker(edtHoraSaida));

        btnSalvar.setOnClickListener(v -> salvarFuncionario());
    }

    private void abrirTimePicker(EditText campo) {
        Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minuto = c.get(Calendar.MINUTE);

        new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            String horaFormatada = String.format("%02d:%02d", hourOfDay, minute);
            campo.setText(horaFormatada);
        }, hora, minuto, true).show();
    }

    private void salvarFuncionario() {
        String nome = edtNome.getText().toString();
        String senha = edtSenha.getText().toString();
        String entrada = edtHoraEntrada.getText().toString();
        String saida = edtHoraSaida.getText().toString();

        if (nome.isEmpty() || senha.isEmpty() || entrada.isEmpty() || saida.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Funcionario novoFuncionario = new Funcionario(nome, "offline", senha, entrada, saida);
        DadosFuncionario.getListaFuncionarios().add(novoFuncionario); // lista temporária

        Toast.makeText(this, "Funcionário criado!", Toast.LENGTH_SHORT).show();
        finish(); // volta para o fragmento do gerente
    }
    }
