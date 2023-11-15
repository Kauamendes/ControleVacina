package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Usuario;
import com.example.demo.repository.UsuarioRepository;

@Controller
@RequestMapping("/login")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    public String insert(Usuario login) {
        if (!repository.findByLogin(login)) {
            return "redirect:/login";
        }
        return "redirect:/vacinas";
    }

    @GetMapping
    public String telaLogin() {
        return "login";
    }
}
