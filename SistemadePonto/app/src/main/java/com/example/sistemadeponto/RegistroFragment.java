package com.example.sistemadeponto;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseFirestore db;
    private RecyclerView PontosView;
    private List<Ponto> listaPontos = new ArrayList<>();
    private PontoAdapter adapter;
    private Ponto pontoEditando = null;
    private TextView txtHorarioEntrada1, txtHorarioSaida1, txtHistorico;
    private Button btnRegistrarEntrada, btnRegistrarSaida;
    private Handler handler = new Handler();
    private Runnable atualizarRelogio;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistroFragment newInstance(String param1, String param2) {
        RegistroFragment fragment = new RegistroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro, container, false);

        // Conecta os elementos
        db = FirebaseFirestore.getInstance();
        txtHorarioEntrada1 = view.findViewById(R.id.txtHorarioEntrada1);
        txtHorarioSaida1 = view.findViewById(R.id.txtHorarioSaida1);
        txtHistorico = view.findViewById(R.id.txtHistorico);
        btnRegistrarEntrada = view.findViewById(R.id.btnRegistrarEntrada);
        btnRegistrarSaida = view.findViewById(R.id.btnRegistrarSaida);
        PontosView = view.findViewById(R.id.PontosView);
        PontosView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PontoAdapter(listaPontos);
        PontosView.setAdapter(adapter);

        // Ação ao clicar em "Registrar Entrada"
        btnRegistrarEntrada.setOnClickListener(v -> {
            String horaAtual = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            txtHorarioEntrada1.setText("Entrada: " + horaAtual);

            String historico = String.valueOf(System.currentTimeMillis());

            Ponto novoPonto = new Ponto(null, horaAtual, "", historico);

            db.collection("pontos")
                    .document(historico)
                    .set(novoPonto)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "Entrada registrada!", Toast.LENGTH_SHORT).show();
                        carregarPontos();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Erro ao registrar", Toast.LENGTH_SHORT).show();
                    });

            DatabaseHelper dbHelper = new DatabaseHelper(getContext());
            Funcionario funcionarioLogado = FuncionarioStorage.getFuncionarioLogado();
            if (funcionarioLogado != null) {
                dbHelper.inserirStatus(funcionarioLogado.getNome(), "online");
                funcionarioLogado.setStatus("online");
                Toast.makeText(getContext(), "Status alterado para ONLINE", Toast.LENGTH_SHORT).show();
            }
        });

        // Ação ao clicar em "Registrar Saída"
        btnRegistrarSaida.setOnClickListener(v -> {
            String horaAtual = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            txtHorarioSaida1.setText("Saída: " + horaAtual);
            txtHistorico.append("\nSaída registrada às " + horaAtual);

            // Procurar último ponto sem saída
            for (int i = listaPontos.size() - 1; i >= 0; i--) {
                Ponto ponto = listaPontos.get(i);
                if (ponto.getSaida() == null || ponto.getSaida().isEmpty()) {
                    ponto.setSaida(horaAtual);
                    db.collection("pontos").document(ponto.getHistorico())
                            .set(ponto)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(getContext(), "Saída registrada!", Toast.LENGTH_SHORT).show();
                                carregarPontos();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getContext(), "Erro ao registrar saída", Toast.LENGTH_SHORT).show();
                            });
                    break;
                }
            }

            DatabaseHelper dbHelper = new DatabaseHelper(getContext());
            Funcionario funcionarioLogado = FuncionarioStorage.getFuncionarioLogado();
            if (funcionarioLogado != null) {
                dbHelper.inserirStatus(funcionarioLogado.getNome(), "offline");
                funcionarioLogado.setStatus("offline");
                Toast.makeText(getContext(), "Status alterado para OFFLINE", Toast.LENGTH_SHORT).show();
            }
        });

        carregarPontos();
        return view;
    }

    private void carregarPontos() {
        db.collection("pontos")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listaPontos.clear();
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Ponto ponto = doc.toObject(Ponto.class);
                            listaPontos.add(ponto);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void limparCampos() {
        txtHorarioEntrada1.setText("");
        txtHorarioSaida1.setText("");
        txtHistorico.setText("");
        pontoEditando = null; // Renomeado para refletir o objeto correto
        btnRegistrarSaida.setText("Registrar Saída");
    }

    private void salvarPonto() {
        if (pontoEditando == null) {
            Toast.makeText(getContext(), "Nenhum ponto selecionado para edição", Toast.LENGTH_SHORT).show();
            return;
        }

        String novaSaida = txtHorarioSaida1.getText().toString().trim();

        if (novaSaida.isEmpty()) {
            Toast.makeText(getContext(), "Hora de saída está vazia!", Toast.LENGTH_SHORT).show();
            return;
        }

        pontoEditando.setSaida(novaSaida);

        db.collection("pontos").document(pontoEditando.getHistorico())
                .set(pontoEditando)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Ponto atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    limparCampos();
                    carregarPontos(); // Recarrega os dados atualizados na lista
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Erro ao atualizar ponto: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
