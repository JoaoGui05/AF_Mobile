package com.example.sistemadeponto;

public class Notificacao {
    private int id;
    private String titulo;
    private String mensagem;
    private String data;

    public Notificacao(int id, String titulo, String mensagem, String data) {
        this.id = id;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.data = data;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getMensagem() { return mensagem; }
    public String getData() { return data; }
}

