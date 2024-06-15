package com.example.demo.domain;

import com.example.demo.utils.NomeVariaveisSessao;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mensagem {

    private String mensagem;
    private String nomeVariavelSessao;

    public static Mensagem fabricarMensagemSalvo(String objeto) {
        return Mensagem.builder()
                .mensagem(String.format("%s salva com sucesso!", objeto))
                .nomeVariavelSessao(NomeVariaveisSessao.MSG_SUCESSO)
                .build();
    }

    public static Mensagem fabricarMensagemEditado(String objeto) {
        return Mensagem.builder()
                .mensagem(String.format("%s editado com sucesso!", objeto))
                .nomeVariavelSessao(NomeVariaveisSessao.MSG_SUCESSO)
                .build();
    }

    public static Mensagem fabricarMensagemErro(String objeto) {
        return Mensagem.builder()
                .mensagem(String.format("Erro ao salvar %s", objeto))
                .nomeVariavelSessao(NomeVariaveisSessao.MSG_ERRO)
                .build();
    }

    public static Mensagem fabricarMensagemNaoEncontrado(String objeto) {
        return Mensagem.builder()
                .mensagem(String.format("%s não encontrado!", objeto))
                .nomeVariavelSessao(NomeVariaveisSessao.MSG_ERRO)
                .build();
    }
}
