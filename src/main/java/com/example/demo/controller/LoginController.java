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
    public ModelAndView telaLogin(HttpSession session) {
        ModelAndView mv = new ModelAndView("login");
        String msgErro = (String) session.getAttribute("msgErro");
        if (msgErro != null) mv.addObject("msgErro", msgErro);
        return mv;
    }

    @GetMapping("/sair")
    public String sair(HttpSession session) {
        session.removeAttribute("cargo");
        return "login";
    }

    @GetMapping("/new_senha")
    public ModelAndView redirectAlteracaoSenha(HttpSession session) {
        ModelAndView mv = new ModelAndView("form_alteracao_senha");
        String mensagem = (String) session.getAttribute("msgSalva");
        if (mensagem != null) {
            mv.addObject("msgSalva", mensagem);
        }
        return mv;
    }

    @PostMapping("/new_senha")
    public String saveNewSenha(AlteracaoSenhaDto loginUpdateDto, HttpSession session) {
        service.saveNewSenha(loginUpdateDto, session);
        return "redirect:/new_senha";
    }
}
