package com.example.sistemadeponto;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RotaAdapter extends RecyclerView.Adapter<RotaAdapter.RotaViewHolder> {

private Context context;
private List<Ronda> listaRondas;

public RotaAdapter(Context context, List<Ronda> listaRondas) {
    this.context = context;
    this.listaRondas = listaRondas;
}

@NonNull
@Override
public RotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_ronda, parent, false);
    return new RotaViewHolder(view);
}

@Override
public void onBindViewHolder(@NonNull RotaViewHolder holder, int position) {
    Ronda ronda = listaRondas.get(position);

    holder.txtNome.setText(ronda.getNome());
    holder.txtHorario.setText(ronda.getHorarioInicio() + " - " + ronda.getHorarioFim());

    holder.btnIniciar.setOnClickListener(v -> {
        // Atualiza o status do funcionário no banco
        Funcionario funcionario = FuncionarioStorage.getFuncionarioLogado();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        dbHelper.inserirStatus(funcionario.getNome(), "em rota");

        // Inicia a tela de detalhes
        Intent intent = new Intent(context, DetalhesRota.class);
        intent.putExtra("nomeRonda", ronda.getNome()); // Se quiser passar dados
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Necessário se estiver usando contexto de aplicação
        context.startActivity(intent);
    });
}

@Override
public int getItemCount() {
    return listaRondas.size();
}

public static class RotaViewHolder extends RecyclerView.ViewHolder {
    TextView txtNome, txtHorario;
    Button btnIniciar;

        public RotaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.nomeRonda);
            txtHorario = itemView.findViewById(R.id.horarioRonda);
            btnIniciar = itemView.findViewById(R.id.btnIniciarRonda);
        }
    }
}