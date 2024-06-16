package com.example.demo.services.impl;

import com.example.demo.services.UsuarioService;
import com.example.demo.utils.NomeVariaveisSessao;
import com.example.demo.domain.Usuario;
import com.example.demo.dto.AlteracaoSenhaDto;
import com.example.demo.dto.UsuarioDto;
import com.example.demo.enums.CargoEnum;
import com.example.demo.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private static final String REDIRECT_ALTERACAO_USUARIO = "redirect:/usuarios/new_senha";

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @CacheEvict("usuarios")
    public String salvar(UsuarioDto usuarioDto, HttpSession session) {
        Usuario usuarioSalvo = usuarioRepository.findByLogin(usuarioDto.getLogin());
        if (usuarioSalvo != null) {
            session.setAttribute(NomeVariaveisSessao.MSG_ERRO, "Já existe um usuário com esse login!");
            return REDIRECT_ALTERACAO_USUARIO;
        }
        if (!usuarioDto.getSenha().equals(usuarioDto.getConfirmacaoSenha())) {
            session.setAttribute(NomeVariaveisSessao.MSG_ERRO, "Senhas diferentes!");
            return "redirect:/usuarios";
        }

        Usuario usuario = Usuario.builder()
                .login(usuarioDto.getLogin())
                .senha(usuarioDto.getSenha())
                .cargo(CargoEnum.valueOf(usuarioDto.getCargo()))
                .build();

        usuarioRepository.save(usuario);
        session.setAttribute(NomeVariaveisSessao.MSG_SUCESSO, "usuário cadastrado com sucesso!");
        return "redirect:/usuarios";
    }

    public void verificaCargoSessao(HttpSession session, HttpServletResponse response) throws IOException {
        CargoEnum cargo = (CargoEnum) session.getAttribute(NomeVariaveisSessao.CARGO);
        if (cargo == null) {
            response.sendRedirect("/");
        } else if (cargo.equals(CargoEnum.APLICADOR)) {
            response.sendRedirect("/vacinas");
        } else if (cargo.equals(CargoEnum.GESTOR)) {
            response.sendRedirect("/relatorios");
        }
    }

    @CacheEvict("usuarios")
    public String alterarSenha(AlteracaoSenhaDto loginUpdateDto, HttpSession session) {
        Usuario usuario = usuarioRepository.findByLogin(loginUpdateDto.getLogin_update());
        if (usuario == null) {
            session.setAttribute(NomeVariaveisSessao.MSG_ERRO, "usuario não encontrado!");
            return REDIRECT_ALTERACAO_USUARIO;
        }
        if (!loginUpdateDto.getSenha_update().equals(loginUpdateDto.getConfirmacaoSenha_update())) {
            session.setAttribute(NomeVariaveisSessao.MSG_ERRO, "Senhas diferentes");
            return REDIRECT_ALTERACAO_USUARIO;
        }
        usuario.setSenha(loginUpdateDto.getSenha_update());
        usuarioRepository.save(usuario);
        session.setAttribute(NomeVariaveisSessao.MSG_SUCESSO, "Alteração salva com sucesso");
        return REDIRECT_ALTERACAO_USUARIO;
    }
}
