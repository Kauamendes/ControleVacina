package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.NomeVariaveisSessao;
import com.example.demo.domain.Usuario;
import com.example.demo.dto.UsuarioDto;
import com.example.demo.services.UsuarioService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public String insert(UsuarioDto usuarioDto, HttpSession session) {
        return service.insert(usuarioDto, session);
    }

    @GetMapping
    public ModelAndView telaCadastroUsuario(HttpSession session, HttpServletResponse response) throws IOException {
        service.verificaCargoSessao(session, response);
        ModelAndView mv = new ModelAndView("cadastro_usuario");

        String msgSalva = (String) session.getAttribute(NomeVariaveisSessao.MSG_SALVO);
        String msgErro = (String) session.getAttribute(NomeVariaveisSessao.MSG_ERRO);

        if (msgSalva != null)
            mv.addObject(NomeVariaveisSessao.MSG_SALVO, msgSalva);
        if (msgErro != null)
            mv.addObject(NomeVariaveisSessao.MSG_ERRO, msgErro);
        mv.addObject("cargos", Usuario.getAllCargos());

        session.removeAttribute(NomeVariaveisSessao.MSG_SALVO);
        session.removeAttribute(NomeVariaveisSessao.MSG_ERRO);
        return mv;
    }
}
