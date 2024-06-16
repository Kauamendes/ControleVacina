package com.example.demo.services;

import com.example.demo.dto.AlteracaoSenhaDto;
import com.example.demo.dto.UsuarioDto;
import jakarta.servlet.http.HttpSession;

public interface UsuarioService {

    String salvar(UsuarioDto usuarioDto, HttpSession session);
    String alterarSenha(AlteracaoSenhaDto loginUpdateDto, HttpSession session);

}
