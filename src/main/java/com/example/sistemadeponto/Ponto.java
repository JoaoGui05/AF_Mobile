package com.example.sistemadeponto;

import com.google.firebase.Timestamp;

public class Ponto {

    private String id;
    private String Entrada;
    private String Saida;
    private String Historico;

    public Ponto(){}

    public Ponto(Timestamp agora, String horaAtual, String saida, String historico) {}

    public Ponto(String id, String entrada, String saida, String historico) {
        this.id = id;
        Entrada = entrada;
        Saida = saida;
        Historico = historico;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntrada() {
        return Entrada;
    }

    public void setEntrada(String entrada) {
        Entrada = entrada;
    }

    public String getSaida() {
        return Saida;
    }

    public void setSaida(String saida) {
        Saida = saida;
    }

    public String getHistorico() {
        return Historico;
    }

    public void setHistorico(String historico) {
        Historico = historico;
    }
}
