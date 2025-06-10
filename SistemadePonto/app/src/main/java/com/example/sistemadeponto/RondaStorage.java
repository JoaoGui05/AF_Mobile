package com.example.sistemadeponto;

import java.util.ArrayList;
import java.util.List;

public class RondaStorage {
    private static List<Ronda> listaRondas = new ArrayList<>();

    public static void adicionarRonda(Ronda ronda) {
        listaRondas.add(ronda);
    }

    public static List<Ronda> getListaRondas() {
        return listaRondas;
    }

    public static void limparRondas() {
        listaRondas.clear();
    }
}
