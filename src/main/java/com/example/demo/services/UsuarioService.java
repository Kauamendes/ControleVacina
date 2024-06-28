package com.example.demo.services;

import com.example.demo.dto.AlteracaoSenhaDto;
import com.example.demo.dto.UsuarioDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public interface UsuarioService {

    String salvar(UsuarioDto usuarioDto, HttpSession session);
    String alterarSenha(AlteracaoSenhaDto loginUpdateDto, HttpSession session);

    void verificaCargoSessao(HttpSession session, HttpServletResponse response) throws IOException;
}
