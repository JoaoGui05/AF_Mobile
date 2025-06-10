package com.example.sistemadeponto;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioStorage {
    private static List<Funcionario> listaFuncionarios = new ArrayList<>();
    private static Funcionario funcionarioLogado;

    public static void adicionarFuncionario(Funcionario funcionario) {
        listaFuncionarios.add(funcionario);
    }

    public static List<Funcionario> getListaFuncionarios() {
        return listaFuncionarios;
    }

    public static void limparFuncionarios() {
        listaFuncionarios.clear();
    }

    public static void setFuncionarioLogado(Funcionario funcionario) {
        funcionarioLogado = funcionario;
    }
    public static Funcionario getFuncionarioLogado() {
        return funcionarioLogado;
    }
}
