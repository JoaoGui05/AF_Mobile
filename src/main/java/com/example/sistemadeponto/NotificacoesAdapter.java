package com.example.sistemadeponto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificacoesAdapter extends RecyclerView.Adapter<NotificacoesAdapter.NotificacaoViewHolder> {

    private List<String> notificacoes;

    public NotificacoesAdapter(List<String> notificacoes) {
        this.notificacoes = notificacoes;
    }

    @NonNull
    @Override
    public NotificacaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new NotificacaoViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificacoesAdapter.NotificacaoViewHolder holder, int position) {
        holder.textView.setText(notificacoes.get(position));
    }

    @Override
    public int getItemCount() {
        return notificacoes.size();
    }

    static class NotificacaoViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public NotificacaoViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}