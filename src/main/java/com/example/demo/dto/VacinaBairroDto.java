package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VacinaBairroDto {

    private Long id;
    private String bairro;
    private String vacina;
    private String dataAplicacao;
}
