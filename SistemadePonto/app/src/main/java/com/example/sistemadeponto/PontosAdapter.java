package com.example.sistemadeponto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PontosAdapter extends RecyclerView.Adapter<PontosAdapter.PontoViewHolder> {

    private List<PontoRonda> pontos;

    public PontosAdapter(List<PontoRonda> pontos) {
        this.pontos = pontos;
    }

    @NonNull
    @Override
    public PontoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new PontoViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull PontoViewHolder holder, int position) {
        PontoRonda ponto = pontos.get(position);
        holder.textView.setText(ponto.getOrdem() + " - " + ponto.getNomePonto());
    }

    @Override
    public int getItemCount() {
        return pontos.size();
    }

    public static class PontoViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public PontoViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
