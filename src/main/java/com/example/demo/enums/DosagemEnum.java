package com.example.demo.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public enum DosagemEnum {

    PRIMEIRA_DOSE("Primeira Dose", 1),
    SEGUNDA_DOSE("Segunda Dose", 2),
    TERCEIRA_DOSE("Terceira Dose", 3),
    UNICA("Unica", 4),
    REFORCO("Reforço", 5);

    private final String nome;
    private final Integer valor;

    DosagemEnum(String nome, Integer valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public static String findByValor(Integer valor) {
        for (DosagemEnum dosagem : DosagemEnum.values()) {
            if (dosagem.valor.equals(valor)) {
                return dosagem.nome;
            }
        }
        return null;
    }

    public static List<String> getDoses() {
        List<String> dosagens = new ArrayList<>();
        for (DosagemEnum dosagem : DosagemEnum.values()) {
            dosagens.add(dosagem.nome);
        }
        return dosagens;
    }
}
