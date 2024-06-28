package com.example.demo.services.impl;

import com.example.demo.domain.Vacina;
import com.example.demo.repository.VacinaRepository;
import com.example.demo.services.VacinaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacinaServiceImpl implements VacinaService {

    private final VacinaRepository vacinaRepository;

    public VacinaServiceImpl(VacinaRepository vacinaRepository) {
        this.vacinaRepository = vacinaRepository;
    }

    @Override
    public List<Vacina> listar() {
        return vacinaRepository.findAll();
    }
}
