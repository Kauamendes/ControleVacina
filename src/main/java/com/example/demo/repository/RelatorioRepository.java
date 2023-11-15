package com.example.demo.repository;

import com.example.demo.dto.RelatorioDto;
import com.example.demo.dto.VacinaBairroDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RelatorioRepository {
    public List<VacinaBairroDto> buscar(RelatorioDto relatorioDto) {
        List<VacinaBairroDto> vacinas = new ArrayList<>();
        return vacinas;
    }
}
