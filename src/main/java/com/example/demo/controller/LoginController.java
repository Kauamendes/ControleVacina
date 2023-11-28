package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.LoginDto;
import com.example.demo.services.LoginService;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private LoginService service;

    @PostMapping
    public String login(LoginDto loginDto) {
        return service.findByAccess(loginDto);
    }

    @GetMapping
    public String telaLogin() {
        return "login";
    }

    @GetMapping("new_senha")
    public String redirectAlteracaoSenha() {
        return "";
    }
}
