package com.example.demo.services;

import com.example.demo.dto.VacinaBairroDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.VacinaBairro;
import com.example.demo.repository.VacinaBairroRepository;
import com.example.demo.utils.DateUtils;

@Service
public class VacinaBairroService {

    @Autowired
    private VacinaBairroRepository repository;

    public void insert(VacinaBairroDto vacinaBairroDto) {
        VacinaBairro vacinaBairro = VacinaBairro.builder()
                .bairroId(Long.valueOf(vacinaBairroDto.getBairro()))
                .vacinaId(Long.valueOf(vacinaBairroDto.getVacina()))
                .dataAplicacao(DateUtils.getStringDataHoraAtual())
                .build();
        repository.insert(vacinaBairro);
    }
}
