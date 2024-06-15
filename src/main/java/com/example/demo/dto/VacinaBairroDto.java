package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacinaBairroDto {

    private Long id;
    private String bairro;
    private String vacina;
    private String dataAplicacao;
    private Integer quantidade;
    private String aplicador;
    private String dose;
    private String observacoes;

    public VacinaBairroDto(String bairro, String vacina, Integer quantidade) {
        this.bairro = bairro;
        this.vacina = vacina;
        this.quantidade = quantidade;
    }
}
