package com.example.sistemadeponto;

public class PontoRonda {
    private String nomePonto;
    private int ordem;

    public PontoRonda(String nomePonto, int ordem) {
        this.nomePonto = nomePonto;
        this.ordem = ordem;
    }

    public String getNomePonto() {
        return nomePonto;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setNomePonto(String nomePonto) {
        this.nomePonto = nomePonto;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }
}