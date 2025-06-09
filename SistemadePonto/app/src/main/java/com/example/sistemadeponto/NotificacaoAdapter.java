package com.example.sistemadeponto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificacaoAdapter extends RecyclerView.Adapter<NotificacaoAdapter.ViewHolder> {

    private final List<Notificacao> listaNotificacoes;

    public NotificacaoAdapter(List<Notificacao> listaNotificacoes) {
        this.listaNotificacoes = listaNotificacoes;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvMensagem, tvData;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvMensagem = itemView.findViewById(R.id.tvMensagem);
            tvData = itemView.findViewById(R.id.tvData);
        }
    }

    @NonNull
    @Override
    public NotificacaoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notificacao, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificacaoAdapter.ViewHolder holder, int position) {
        Notificacao notificacao = listaNotificacoes.get(position);
        holder.tvTitulo.setText(notificacao.getTitulo());
        holder.tvMensagem.setText(notificacao.getMensagem());
        holder.tvData.setText(notificacao.getData());
    }

    @Override
    public int getItemCount() {
        return listaNotificacoes.size();
    }
}
