package com.example.demo.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String dose;
    private String aplicador;
    private String observacoes;
}
