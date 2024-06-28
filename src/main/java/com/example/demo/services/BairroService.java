package com.example.demo.services;

import com.example.demo.domain.Bairro;
import com.example.demo.domain.Vacina;

import java.util.List;

public interface BairroService {

    Bairro buscarPorNome(String nomeBairro);

    List<Bairro> listar();
}
