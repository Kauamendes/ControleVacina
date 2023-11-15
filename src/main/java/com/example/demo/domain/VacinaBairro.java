package com.example.demo.domain;

import java.util.Date;

import com.example.demo.dto.VacinaBairroDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VacinaBairro {

    private Long id;
    private Long vacina_id;
    private Long bairro_id;
    private String data_aplicacao;

    public VacinaBairro(VacinaBairroDto dto) {
        this.vacina_id = Long.parseLong(dto.getVacina_id());
        this.bairro_id = Long.parseLong(dto.getBairro_id());
        this.data_aplicacao = dto.getData_aplicacao();
    }
}
