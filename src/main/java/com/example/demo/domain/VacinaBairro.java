package com.example.demo.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VacinaBairro {

    private Long id;
    private Long vacinaId;
    private Long bairroId;
    private String dataAplicacao;
}
