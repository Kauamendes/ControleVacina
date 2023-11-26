package com.example.demo.domain;

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
}
