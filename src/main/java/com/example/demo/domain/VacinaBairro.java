package com.example.demo.domain;

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
}
