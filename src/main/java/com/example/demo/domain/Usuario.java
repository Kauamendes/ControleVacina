package com.example.demo.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Usuario {

    private Long id;
    private String login;
    private String senha;
    private String cargo;
}
