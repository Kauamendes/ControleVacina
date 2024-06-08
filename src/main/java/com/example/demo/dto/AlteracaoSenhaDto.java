package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlteracaoSenhaDto {

    private String login_update;
    private String senha_update;
    private String confirmacaoSenha_update;
}
