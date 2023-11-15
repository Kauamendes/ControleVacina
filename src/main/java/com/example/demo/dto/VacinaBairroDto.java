package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VacinaBairroDto {

    private String id;
    private String vacina_id;
    private String bairro_id;
    private String data_aplicacao;
}
