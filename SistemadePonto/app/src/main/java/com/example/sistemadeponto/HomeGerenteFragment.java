package com.example.sistemadeponto;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.sistemadeponto.Funcionario;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeGerenteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeGerenteFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button btnCriarRonda, btnCriarFuncionario;
    private List<Funcionario> listaFuncionarios;
    private FuncionarioAdapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeGerenteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeGerenteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeGerenteFragment newInstance(String param1, String param2) {
        HomeGerenteFragment fragment = new HomeGerenteFragment();
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
        View view = inflater.inflate(R.layout.fragment_home_gerente, container, false);

        recyclerView = view.findViewById(R.id.recyclerFuncionarios);
        btnCriarFuncionario = view.findViewById(R.id.btnCriarFuncionario);
        btnCriarRonda = view.findViewById(R.id.btnCriarRonda);

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        listaFuncionarios = FuncionarioStorage.getListaFuncionarios(); // pega lista salva

        // Atualiza status de cada funcionário com o dado do banco
        for (Funcionario funcionario : listaFuncionarios) {
            String status = dbHelper.buscarStatusAtual(funcionario.getNome());
            funcionario.setStatus(status); // adiciona o status ao objeto
        }

        adapter = new FuncionarioAdapter(listaFuncionarios, getContext()); // você deve ter isso

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        btnCriarFuncionario.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CriarFuncionario.class);
            startActivity(intent);
        });

        btnCriarRonda.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CriarRonda.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        listaFuncionarios.clear();
        listaFuncionarios.addAll(dbHelper.buscarTodosFuncionariosComStatus());
        for (Funcionario funcionario : listaFuncionarios) {
            String status = dbHelper.buscarStatusAtual(funcionario.getNome());
            funcionario.setStatus(status);
            listaFuncionarios.add(funcionario);
        }
        adapter.notifyDataSetChanged(); // atualiza lista quando volta
    }
}
