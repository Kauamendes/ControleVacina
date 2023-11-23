package com.example.demo.services;

import com.example.demo.domain.Bairro;
import com.example.demo.domain.Vacina;
import com.example.demo.dto.VacinaBairroDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.VacinaBairro;
import com.example.demo.repository.VacinaBairroRepository;
import com.example.demo.utils.DateUtils;

import java.sql.SQLException;
import java.util.List;

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
    public List<Bairro> listarBairros() throws SQLException {
        return repository.listarBairros();
    }

    public List<Vacina> listarVacinas() throws SQLException {
        return repository.listarVacinas();
    }
}
