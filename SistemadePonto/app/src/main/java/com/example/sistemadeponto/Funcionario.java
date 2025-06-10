package com.example.sistemadeponto;

public class Funcionario {
    private String nome;
    private String status;
    private String senha;
    private String horaEntrada;
    private String horaSaida;

    public Funcionario() {}
    public Funcionario(String nome, String status, String senha, String horaEntrada, String horaSaida) {
        this.nome = nome;
        this.status = status;
        this.senha = senha;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
    }
    public Funcionario(String nome, String status) {
        this.nome = nome;
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public String getStatus() {
        return status;
    }
    public String getSenha() { return senha; }
    public String getHoraEntrada() { return horaEntrada; }
    public String getHoraSaida() { return horaSaida; }

    public void setStatus(String status) { this.status = status; }
}
