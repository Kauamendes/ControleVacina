package com.example.demo.dto;

import com.example.demo.enums.DosagemEnum;
import lombok.*;

import java.util.Objects;

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
    private Integer qtdRegistros;
    private String aplicador;
    private String dose;
    private String observacoes;

    public VacinaBairroDto(String bairro, String vacina, Integer quantidade) {
        this.bairro = bairro;
        this.vacina = vacina;
        this.quantidade = quantidade;
    }

    public boolean isUnica() {
        return Objects.equals(DosagemEnum.UNICA.getValor().toString(), dose) || Objects.equals(DosagemEnum.UNICA.getNome(), dose);
    }

    public boolean isPrimeiraDose() {
        return Objects.equals(DosagemEnum.PRIMEIRA_DOSE.getValor().toString(), dose) || Objects.equals(DosagemEnum.PRIMEIRA_DOSE.getNome(), dose);
    }

    public boolean isSegundaDose() {
        return Objects.equals(DosagemEnum.SEGUNDA_DOSE.getValor().toString(), dose) || Objects.equals(DosagemEnum.SEGUNDA_DOSE.getNome(), dose);
    }

    public boolean isTerceiraDose() {
        return Objects.equals(DosagemEnum.TERCEIRA_DOSE.getValor().toString(), dose) || Objects.equals(DosagemEnum.TERCEIRA_DOSE.getNome(), dose);
    }

    public boolean isReforco() {
        return Objects.equals(DosagemEnum.REFORCO.getValor().toString(), dose) || Objects.equals(DosagemEnum.REFORCO.getNome(), dose);
    }
}
