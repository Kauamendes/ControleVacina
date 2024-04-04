package com.example.demo.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Vacina {

    private Long id;
    private String nome;
    private boolean dosagem;

    public boolean hasDosagem() {
        return dosagem;
    }
}
