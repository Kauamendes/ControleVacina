package com.example.demo.services;

import com.example.demo.NomeVariaveisSessao;
import com.example.demo.domain.Usuario;
import com.example.demo.dto.AlteracaoSenhaDto;
import com.example.demo.dto.LoginDto;
import com.example.demo.dto.UsuarioDto;
import com.example.demo.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @CacheEvict("usuarios")
    public String insert(UsuarioDto usuarioDto, HttpSession session) {
        Usuario usuarioSalvo = usuarioRepository.findByLogin(usuarioDto.getLogin());
        if (usuarioSalvo != null) {
            session.setAttribute(NomeVariaveisSessao.MSG_ERRO, "Já existe um usuário com esse login!");
            return "redirect:/usuarios/new_senha";
        }
        if (!usuarioDto.getSenha().equals(usuarioDto.getConfirmacaoSenha())) {
            session.setAttribute(NomeVariaveisSessao.MSG_ERRO, "Senhas diferentes!");
            return "redirect:/usuarios";
        }

        Usuario usuario = Usuario.builder()
                .login(usuarioDto.getLogin())
                .senha(usuarioDto.getSenha())
                .cargo(usuarioDto.getCargo())
                .build();

        usuarioRepository.insert(usuario);
        session.setAttribute(NomeVariaveisSessao.MSG_SALVO, "Usuário cadastrado com sucesso!");
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

    @CacheEvict("usuarios")
    public String saveNewSenha(AlteracaoSenhaDto loginUpdateDto, HttpSession session) {
        Usuario usuario = usuarioRepository.findByLogin(loginUpdateDto.getLogin_update());
        if (usuario == null) {
            session.setAttribute(NomeVariaveisSessao.MSG_ERRO, "usuario não encontrado!");
            return "redirect:/usuarios/new_senha";
        }

        if (!loginUpdateDto.getSenha_update().equals(loginUpdateDto.getConfirmacaoSenha_update())) {
            session.setAttribute(NomeVariaveisSessao.MSG_ERRO, "Senhas diferentes");
            return "redirect:/usuarios/new_senha";
        }

        usuario.setSenha(loginUpdateDto.getSenha_update());
        usuarioRepository.updateUsuario(usuario);
        session.setAttribute(NomeVariaveisSessao.MSG_SALVO, "Alteração salva com sucesso");
        return "redirect:/usuarios/new_senha";
    }

    private boolean verificaSeUsuarioAdmin(AlteracaoSenhaDto loginUpdateDto, HttpSession session) {
        String usuarioLogado = (String) session.getAttribute(NomeVariaveisSessao.USUARIO_LOGADO);
        Usuario usuarioAdmin = usuarioRepository.findByLogin(usuarioLogado);
        if (usuarioAdmin == null) {
            session.setAttribute(NomeVariaveisSessao.MSG_ERRO, "Usuário admin inválido!");
            return false;
        } else if (!usuarioAdmin.isAdmin()) {
            session.setAttribute(NomeVariaveisSessao.MSG_ERRO, "Usuário informado não é admin!");
            return false;
        }
        return true;
    }
}
