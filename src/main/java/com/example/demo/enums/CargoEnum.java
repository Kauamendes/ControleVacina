package com.example.demo.enums;

import lombok.Getter;

@Getter
public enum CargoEnum {

    ADMIN("Admin"),
    APLICADOR("Aplicador"),
    GESTOR("Gestor");

    private String nome;

    CargoEnum(String nome) {
        this.nome = nome;
    }
}
