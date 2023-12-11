package com.example.demo.controller;

import java.io.IOException;
import java.sql.SQLException;

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
    public ModelAndView telaCadastroUsuario(HttpSession session, HttpServletResponse response) throws SQLException, IOException {
        service.verificaCargoSessao(session, response);
        ModelAndView mv = new ModelAndView("cadastro_usuario");
        mv.addObject("cargos", Usuario.getAllCargos());
        String msgSalva = (String) session.getAttribute("msgSalva");
        String msgErro = (String) session.getAttribute("msgErro");
        if (msgSalva != null) mv.addObject("msgSalva", msgSalva);
        if (msgErro != null) mv.addObject("msgErro", msgErro);
        return mv;
    }
}
