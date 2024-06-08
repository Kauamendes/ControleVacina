package com.example.demo.domain;

import java.util.Arrays;
import java.util.List;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Usuario {

    public static final String TIP_CARGO_APLICADOR = "Aplicador";
    public static final String TIP_CARGO_GESTOR = "Gestor";
    public static final String TIP_CARGO_ADMIN = "Admin";

    private Long id;
    private String login;
    private String senha;
    private String cargo;

    public boolean isAplicador() {
        return cargo.equals(TIP_CARGO_APLICADOR);
    }

    public boolean isGestor() {
        return cargo.equals(TIP_CARGO_GESTOR);
    }

    public boolean isAdmin() {
        return cargo.equals(TIP_CARGO_ADMIN);
    }

    public static List<String> getAllCargos() {
        return Arrays.asList(TIP_CARGO_ADMIN, TIP_CARGO_APLICADOR, TIP_CARGO_GESTOR);
    }
}
