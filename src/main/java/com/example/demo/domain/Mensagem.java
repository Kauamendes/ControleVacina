package com.example.demo.domain;

import com.example.demo.NomeVariaveisSessao;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mensagem {

    private String mensagem;
    private String nomeVariavelSessao;
}
