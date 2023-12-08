package com.example.demo.enums;

import lombok.Getter;

@Getter
public enum DosagemEnum {

    PRIMEIRA_DOSE("Primeira Dose", 1),
    SEGUNDA_DOSE("Segunda Dose", 2),
    TERCEIRA_DOSE("Terceira Dose", 3),
    UNICA("Unica", 4),
    REFORCO("Reforso", 5);

    private final String nome;
    private final Integer valor;

    DosagemEnum(String nome, Integer valor) {
        this.nome = nome;
        this.valor = valor;
    }
}
