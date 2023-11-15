package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.VacinaBairro;
import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.repository.BairroRepository;
import com.example.demo.repository.VacinaBairroRepository;
import com.example.demo.repository.VacinaRepository;
import com.example.demo.utils.DateUtils;

@Service
public class VacinaBairroService {

    @Autowired
    private VacinaBairroRepository repository;

    public void insert(VacinaBairroDto vacinaBairroDto) throws Exception {
        String data = DateUtils.getStringDataHoraAtual();
        vacinaBairroDto.setData_aplicacao(data);
        repository.insert(new VacinaBairro(vacinaBairroDto));
    }
}
