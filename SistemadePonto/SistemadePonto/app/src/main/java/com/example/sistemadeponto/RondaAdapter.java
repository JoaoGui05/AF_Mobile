package com.example.sistemadeponto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class RondaAdapter extends RecyclerView.Adapter<RondaAdapter.RondaViewHolder> {

    private List<Ronda> listaRondas;

    public RondaAdapter(List<Ronda> listaRondas) {
        this.listaRondas = listaRondas;
    }

    @NonNull
    @Override
    public RondaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ronda, parent, false);
        return new RondaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RondaViewHolder holder, int position) {
        Ronda ronda = listaRondas.get(position);
        holder.nomeTextView.setText(ronda.getNome());
        holder.horarioTextView.setText("Das " + ronda.getHorarioInicio() + " às " + ronda.getHorarioFim());
        holder.funcionariosTextView.setText("Funcionários: " + String.join(", ", ronda.getFuncionarios()));
        holder.pontosTextView.setText("Pontos: " + String.join(", ", ronda.getPontos()));
    }

    @Override
    public int getItemCount() {
        return listaRondas.size();
    }

    public static class RondaViewHolder extends RecyclerView.ViewHolder {
        TextView nomeTextView, horarioTextView, funcionariosTextView, pontosTextView;

        public RondaViewHolder(View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.nomeRonda);
            horarioTextView = itemView.findViewById(R.id.horarioRonda);
            funcionariosTextView = itemView.findViewById(R.id.funcionariosRonda);
            pontosTextView = itemView.findViewById(R.id.pontosRonda);
        }
    }
}

