package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.VacinaBairro;
import com.example.demo.repository.BairroRepository;
import com.example.demo.repository.VacinaBairroRepository;
import com.example.demo.repository.VacinaRepository;

@Service
public class VacinaBairroService {

    @Autowired
    private VacinaBairroRepository repository;
    @Autowired
    private VacinaRepository vacinaRepository;
    @Autowired
    private BairroRepository bairroRepository;

    public void insert(VacinaBairro vacinaBairro) throws Exception {
        if (!vacinaRepository.existsById(vacinaBairro.getVacina_id())) {
            throw new Exception("Vacina informada não consta no banco de dados");
        }
        if (!bairroRepository.existsById(vacinaBairro.getBairro_id())) {
            throw new Exception("Bairro informado não consta no banco de dados");
        }
        repository.insert(vacinaBairro);
    }
}
