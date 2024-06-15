package com.example.demo.mappers;

import com.example.demo.domain.Bairro;
import com.example.demo.domain.Vacina;
import com.example.demo.domain.VacinaBairro;
import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.enums.DosagemEnum;
import org.springframework.stereotype.Component;

@Component
public class VacinaBairroMapper {

    public VacinaBairro toEntity(VacinaBairroDto vacinaBairroDto) {
        return VacinaBairro.builder()
                .id(vacinaBairroDto.getId())
                .vacina(Vacina.builder().id(Long.parseLong(vacinaBairroDto.getVacina())).build())
                .bairro(Bairro.builder().id(Long.parseLong(vacinaBairroDto.getBairro())).build())
                .dose(DosagemEnum.valueOf(vacinaBairroDto.getDose()))
                .aplicador(vacinaBairroDto.getAplicador())
                .observacoes(vacinaBairroDto.getObservacoes())
                .build();
    }

    public VacinaBairroDto toDto(VacinaBairro vacinaBairro) {
        return VacinaBairroDto.builder()
                .id(vacinaBairro.getId())
                .vacina(vacinaBairro.getVacina().getId().toString())
                .bairro(vacinaBairro.getBairro().getId().toString())
                .dose(vacinaBairro.getDose().getNome())
                .aplicador(vacinaBairro.getAplicador())
                .observacoes(vacinaBairro.getObservacoes())
                .dataAplicacao(vacinaBairro.getDataAplicacao().toString())
                .build();
    }

}
