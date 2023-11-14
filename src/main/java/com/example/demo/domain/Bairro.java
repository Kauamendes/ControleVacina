package com.example.demo.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Bairro {

    // possivelmente classe será removida
    private Long id;
    private String nome;
}
