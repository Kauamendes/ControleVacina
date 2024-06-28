package com.example.demo.services;

import com.example.demo.dto.RelatorioDto;
import com.example.demo.dto.VacinaBairroDto;

import java.util.List;

public interface VacinaBairroService {
    
    List<VacinaBairroDto> buscar(RelatorioDto relatorioDto);

    List<VacinaBairroDto> listar(RelatorioDto relatorioDto);

    List<VacinaBairroDto> listarUltimosCadastradosPorUsuario(String usuarioLogado);
}
