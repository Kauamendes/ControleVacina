package com.example.demo.services;

import com.example.demo.NomeVariaveisSessao;
import com.example.demo.domain.Usuario;
import com.example.demo.dto.UsuarioDto;
import com.example.demo.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String insert(UsuarioDto usuarioDto, HttpSession session) {
        if (!usuarioDto.getSenha().equals(usuarioDto.getConfirmacaoSenha())) {
            session.setAttribute(NomeVariaveisSessao.MSG_ERRO, "senhas diferentes!");
            return "redirect:/usuarios";
        }

        Usuario usuario = Usuario.builder()
                .login(usuarioDto.getLogin())
                .senha(usuarioDto.getSenha())
                .cargo(usuarioDto.getCargo())
                .build();

        usuarioRepository.save(usuario);
        session.setAttribute(NomeVariaveisSessao.MSG_SALVO, "usuário cadastrado com sucesso!");
        return "redirect:/usuarios";
    }

    public void verificaCargoSessao(HttpSession session, HttpServletResponse response) throws IOException {
        String cargo = (String) session.getAttribute(NomeVariaveisSessao.CARGO);
        if (cargo == null) {
            response.sendRedirect("/");
        } else if (cargo.equals(Usuario.TIP_CARGO_APLICADOR)) {
            response.sendRedirect("/vacinas");
        } else if (cargo.equals(Usuario.TIP_CARGO_GESTOR)) {
            response.sendRedirect("/relatorios");
        }
    }
}
