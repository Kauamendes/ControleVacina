package com.example.demo.domain;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VacinaBairro {

    private Long id;
    private Long vacinaId;
    private Long bairroId;
    private Timestamp dataAplicacao;
}
