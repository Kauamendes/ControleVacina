package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.VacinaBairro;
import com.example.demo.repository.VacinaBairroRepository;
import com.example.demo.utils.DateUtils;

@Service
public class VacinaBairroService {

    @Autowired
    private VacinaBairroRepository repository;

    public void insert(VacinaBairro vacinaBairro) throws Exception {
        String data = DateUtils.getStringDataHoraAtual();
        vacinaBairro.setData_aplicacao(data);
        repository.insert(vacinaBairro);
    }
}
