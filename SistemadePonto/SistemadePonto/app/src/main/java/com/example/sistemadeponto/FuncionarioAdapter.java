package com.example.sistemadeponto;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FuncionarioAdapter extends RecyclerView.Adapter<FuncionarioAdapter.FuncionarioViewHolder> {

    private List<Funcionario> listaFuncionarios;

    public Context context;
    public FuncionarioAdapter(List<Funcionario> listaFuncionarios, Context context) {
        this.listaFuncionarios = listaFuncionarios;
        this.context = context;
    }
    @NonNull
    @Override
    public FuncionarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_funcionario, parent, false);
        return new FuncionarioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FuncionarioViewHolder holder, int position) {
        Funcionario funcionario = listaFuncionarios.get(position);
        holder.nomeTextView.setText(funcionario.getNome());
        holder.statusTextView.setText(funcionario.getStatus());
        // Define a cor do status
        switch (funcionario.getStatus()) {
            case "offline":
                holder.statusTextView.setTextColor(Color.RED);
                break;
            case "online":
                holder.statusTextView.setTextColor(Color.GREEN);
                break;
            case "em rota":
                holder.statusTextView.setTextColor(Color.BLUE);
                break;
            default:
                holder.statusTextView.setTextColor(Color.DKGRAY);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listaFuncionarios.size();
    }

    public static class FuncionarioViewHolder extends RecyclerView.ViewHolder {
        TextView nomeTextView;
        TextView statusTextView;

        public FuncionarioViewHolder(View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.nomeFuncionario);
            statusTextView = itemView.findViewById(R.id.statusFuncionario);
        }
    }
}
