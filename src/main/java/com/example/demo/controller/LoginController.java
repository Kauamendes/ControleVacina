package com.example.demo.controller;

import com.example.demo.NomeVariaveisSessao;
import com.example.demo.dto.AlteracaoSenhaDto;
import com.example.demo.dto.LoginDto;
import com.example.demo.services.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private LoginService service;

    @PostMapping
    public void login(LoginDto loginDto, HttpSession session, HttpServletResponse response) throws IOException {
        service.findByAccess(loginDto, session, response);
    }

    @GetMapping
    public ModelAndView telaLogin(HttpSession session) {
        ModelAndView mv = new ModelAndView("login");
        String msgErro = (String) session.getAttribute(NomeVariaveisSessao.MSG_ERRO);
        if (msgErro != null) mv.addObject(NomeVariaveisSessao.MSG_ERRO, msgErro);
        return mv;
    }

    @GetMapping("/sair")
    public String sair(HttpSession session) {
        session.removeAttribute(NomeVariaveisSessao.CARGO);
        session.removeAttribute(NomeVariaveisSessao.USUARIO_LOGADO);
        session.removeAttribute(NomeVariaveisSessao.VACINA);
        session.removeAttribute(NomeVariaveisSessao.BAIRRO);
        session.removeAttribute(NomeVariaveisSessao.EDITANDO_ID);
        return "redirect:/";
    }
}
