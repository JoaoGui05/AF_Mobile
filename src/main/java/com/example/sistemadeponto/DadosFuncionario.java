package com.example.sistemadeponto;

import java.util.ArrayList;
import java.util.List;
public class DadosFuncionario {
    private static List<Funcionario> listaFuncionarios = new ArrayList<>();

    public static List<Funcionario> getListaFuncionarios() {
        return listaFuncionarios;
    }
}
