package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.AlteracaoSenhaDto;
import com.example.demo.dto.LoginDto;
import com.example.demo.services.LoginService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private LoginService service;

    @PostMapping
    public String login(LoginDto loginDto, HttpSession session) {
        return service.findByAccess(loginDto, session);
    }

    @GetMapping
    public String telaLogin() {
        return "login";
    }

    @GetMapping("/sair")
    public String sair(HttpSession session) {
        session.removeAttribute("cargo");
        return "login";
    }

    @GetMapping("/new_senha")
    public ModelAndView redirectAlteracaoSenha(HttpSession session) {
        ModelAndView mv = new ModelAndView("form_alteracao_senha");
        String mensagem = (String) session.getAttribute("mensagemSalva");
        if (mensagem != null) {
            mv.addObject("mensagemSalva", mensagem);
        }
        return mv;
    }

    @PostMapping("/new_senha")
    public String saveNewSenha(AlteracaoSenhaDto loginUpdateDto, HttpSession session) {
        session.setAttribute("mensagemSalva", "Alteração salva com sucesso");
        return "redirect:/new_senha";
    }
}
