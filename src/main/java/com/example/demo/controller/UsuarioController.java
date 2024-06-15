package com.example.demo.controller;

import java.io.IOException;

import com.example.demo.utils.NomeVariaveisSessao;
import com.example.demo.dto.AlteracaoSenhaDto;
import com.example.demo.enums.CargoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

        String msgSalva = (String) session.getAttribute(NomeVariaveisSessao.MSG_SUCESSO);
        String msgErro = (String) session.getAttribute(NomeVariaveisSessao.MSG_ERRO);
        CargoEnum cargo = (CargoEnum) session.getAttribute(NomeVariaveisSessao.CARGO);

        if (msgSalva != null) mv.addObject(NomeVariaveisSessao.MSG_SUCESSO, msgSalva);
        if (msgErro != null) mv.addObject(NomeVariaveisSessao.MSG_ERRO, msgErro);
        if (cargo != null) mv.addObject(NomeVariaveisSessao.CARGO, cargo.toString());
        mv.addObject("cargos", Usuario.getAllCargos());

        session.removeAttribute(NomeVariaveisSessao.MSG_SUCESSO);
        session.removeAttribute(NomeVariaveisSessao.MSG_ERRO);
        return mv;
    }

    @GetMapping("/new_senha")
    public ModelAndView telaAlteracaoUsuario(HttpSession session, HttpServletResponse response) throws IOException {
        service.verificaCargoSessao(session, response);
        ModelAndView mv = new ModelAndView("form_alteracao_senha");

        String msgSalva = (String) session.getAttribute(NomeVariaveisSessao.MSG_SUCESSO);
        String msgErro = (String) session.getAttribute(NomeVariaveisSessao.MSG_ERRO);
        CargoEnum cargo = (CargoEnum) session.getAttribute(NomeVariaveisSessao.CARGO);

        if (msgSalva != null) mv.addObject(NomeVariaveisSessao.MSG_SUCESSO, msgSalva);
        if (msgErro != null) mv.addObject(NomeVariaveisSessao.MSG_ERRO, msgErro);
        if (cargo != null) mv.addObject(NomeVariaveisSessao.CARGO, cargo.toString());
        mv.addObject("cargos", Usuario.getAllCargos());

        session.removeAttribute(NomeVariaveisSessao.MSG_SUCESSO);
        session.removeAttribute(NomeVariaveisSessao.MSG_ERRO);
        return mv;
    }

    @PostMapping("/new_senha")
    public String saveNewSenha(AlteracaoSenhaDto loginUpdateDto, HttpSession session) {
       return service.saveNewSenha(loginUpdateDto, session);
    }
}
