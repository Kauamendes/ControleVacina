package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class VacinaBairroDto {

    private Long id;
    private String bairro;
    private String vacina;
    private Date dataAplicacao;
}
