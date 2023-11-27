package com.example.demo.services;

import com.example.demo.domain.Bairro;
import com.example.demo.domain.Vacina;
import com.example.demo.domain.VacinaBairro;
import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.repository.VacinaBairroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
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
                .dataAplicacao(Date.valueOf(vacinaBairroDto.getDataAplicacao()))
                .build();
        repository.insert(vacinaBairro);
    }

    public List<Bairro> listarBairro() {
        return repository.listarBairros();
    }

    public List<Vacina> listarVacinas() {
        return repository.listarVacinas();
    }

    public Bairro buscarBairroPorNome(String nomeBairro) throws SQLException {
        return repository.buscarBairroPorNome(nomeBairro);
    }
}
