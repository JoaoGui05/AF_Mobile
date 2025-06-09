package com.example.sistemadeponto;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CriarRonda extends AppCompatActivity {

    private EditText nomeRondaEditText;
    private TimePickerDialog timePickerDialog;
    private TextView horarioInicioTextView, horarioFimTextView;
    private Button btnAdicionarPonto, btnSalvarRonda;
    private RecyclerView recyclerPontos;
    private List<PontoRonda> listaPontos = new ArrayList<>();
    private PontosAdapter pontosAdapter = new PontosAdapter(listaPontos);
    private LinearLayout listaFuncionariosLayout;
    private List<Funcionario> funcionariosCadastrados;
    private List<CheckBox> checkBoxFuncionarios;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_ronda);

        nomeRondaEditText = findViewById(R.id.nomeRondaEditText);
        horarioInicioTextView = findViewById(R.id.horarioInicioTextView);
        horarioFimTextView = findViewById(R.id.horarioFimTextView);
        btnAdicionarPonto = findViewById(R.id.btnAdicionarPonto);
        btnSalvarRonda = findViewById(R.id.btnSalvarRonda);
        recyclerPontos = findViewById(R.id.recyclerPontos);
        listaFuncionariosLayout = findViewById(R.id.main);

        listaPontos = new ArrayList<>();
        funcionariosCadastrados = FuncionarioStorage.getListaFuncionarios(); // Supondo uma classe que guarda os dados
        checkBoxFuncionarios = new ArrayList<>();

        pontosAdapter = new PontosAdapter(listaPontos);
        recyclerPontos.setLayoutManager(new LinearLayoutManager(this));
        recyclerPontos.setAdapter(pontosAdapter);

        preencherListaFuncionarios();

        horarioInicioTextView.setOnClickListener(v -> mostrarTimePicker(horarioInicioTextView));
        horarioFimTextView.setOnClickListener(v -> mostrarTimePicker(horarioFimTextView));

        btnAdicionarPonto.setOnClickListener(v -> adicionarNovoPonto());

        btnSalvarRonda.setOnClickListener(v -> salvarRonda());
    }

    private void preencherListaFuncionarios() {
        for (Funcionario f : funcionariosCadastrados) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(f.getNome());
            listaFuncionariosLayout.addView(checkBox);
            checkBoxFuncionarios.add(checkBox);
        }
    }

    private void mostrarTimePicker(TextView targetView) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute1) -> targetView.setText(String.format("%02d:%02d", hourOfDay, minute1)),
                hour, minute, true);
        timePickerDialog.show();
    }

    private void adicionarNovoPonto() {
        EditText input = new EditText(this);
        new android.app.AlertDialog.Builder(this)
                .setTitle("Novo Ponto de Ronda")
                .setMessage("Descrição do ponto:")
                .setView(input)
                .setPositiveButton("Adicionar", (dialog, which) -> {
                    String ponto = input.getText().toString();
                    if (!ponto.isEmpty()) {
                        int ordem = listaPontos.size() + 1;
                        PontoRonda novoPonto = new PontoRonda(ponto, ordem);
                        listaPontos.add(novoPonto);
                        pontosAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void salvarRonda() {
        String nome = nomeRondaEditText.getText().toString();
        String inicio = horarioInicioTextView.getText().toString();
        String fim = horarioFimTextView.getText().toString();

        List<String> funcionariosSelecionados = new ArrayList<>();
        for (CheckBox cb : checkBoxFuncionarios) {
            if (cb.isChecked()) {
                funcionariosSelecionados.add(cb.getText().toString());
            }
        }

        // Aqui você pode salvar esses dados em banco, arquivo ou enviar para servidor futuramente
        Toast.makeText(this, "Ronda '" + nome + "' salva com " + funcionariosSelecionados.size() + " funcionário(s)", Toast.LENGTH_LONG).show();
        finish();
    }
}
