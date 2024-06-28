package com.example.demo.services.impl;

import com.example.demo.domain.Bairro;
import com.example.demo.domain.Vacina;
import com.example.demo.repository.BairroRepository;
import com.example.demo.services.BairroService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BairroServiceImpl implements BairroService {

    private final BairroRepository bairroRepository;

    public BairroServiceImpl(BairroRepository bairroRepository) {
        this.bairroRepository = bairroRepository;
    }

    @Override
    public Bairro buscarPorNome(String nomeBairro) {
        return bairroRepository.findByNome(nomeBairro).orElseThrow();
    }

    @Override
    public List<Bairro> listar() {
        return bairroRepository.findAll();
    }
}
