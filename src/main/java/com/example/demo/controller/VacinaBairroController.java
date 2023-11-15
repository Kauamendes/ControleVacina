package com.example.demo.controller;

import com.example.demo.dto.VacinaBairroDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.VacinaBairro;
import com.example.demo.services.VacinaBairroService;

@Controller
@RequestMapping("/vacinas")
public class VacinaBairroController {

    @Autowired
    private VacinaBairroService service;

    @GetMapping
    public String telaCadastroVacina() {
        return "cadastro_vacina";
    }

    @PostMapping
    public String insert(VacinaBairroDto vacinaBairroDto) throws Exception {
        service.insert(vacinaBairroDto);
        return "redirect:/vacinas";
    }
}
