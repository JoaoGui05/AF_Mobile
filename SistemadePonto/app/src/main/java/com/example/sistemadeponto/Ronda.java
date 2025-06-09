package com.example.sistemadeponto;

import java.util.List;
public class Ronda {
    private String nome;
    private String horarioInicio;
    private String horarioFim;
    private List<String> funcionarios;
    private List<String> pontos;

    public Ronda() {
    }

    public Ronda(String nome, String horarioInicio, String horarioFim, List<String> funcionarios, List<String> pontos) {
        this.nome = nome;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.funcionarios = funcionarios;
        this.pontos = pontos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public String getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(String horarioFim) {
        this.horarioFim = horarioFim;
    }

    public List<String> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<String> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public List<String> getPontos() {
        return pontos;
    }

    public void setPontos(List<String> pontos) {
        this.pontos = pontos;
    }
}
