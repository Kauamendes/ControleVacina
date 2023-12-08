package com.example.demo.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Vacina {

    public static final String POSSUI_DOSAGEM = "S";

    private Long id;
    private String nome;
}
