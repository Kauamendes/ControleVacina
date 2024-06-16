package com.example.demo.services.impl;

import com.example.demo.services.LoginService;
import com.example.demo.utils.NomeVariaveisSessao;
import com.example.demo.domain.Usuario;
import com.example.demo.dto.LoginDto;
import com.example.demo.repository.UsuarioRepository;
import jakarta.persistence.Cacheable;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public LoginServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String logar(LoginDto login, HttpSession session) {
        Usuario usuarioLogado = usuarioRepository.findByLoginIgnoreCaseAndSenha(login.getLogin(), login.getSenha());
        if (usuarioLogado == null) {
            session.setAttribute(NomeVariaveisSessao.MSG_ERRO, "Usuário ou senha inválidos!");
            return "redirect:/";
        }

        session.setAttribute(NomeVariaveisSessao.CARGO, usuarioLogado.getCargo());
        session.setAttribute(NomeVariaveisSessao.USUARIO_LOGADO, usuarioLogado.getLogin());
        removerAtributosSessao(session);
        if (usuarioLogado.isAplicador()) {
            return "redirect:/vacinas";
        }
        if (usuarioLogado.isAdmin()) {
            return "redirect:/usuarios";
        }
        return "redirect:/relatorios";
    }

    @Cacheable("usuarios")
    private Usuario buscarPorLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    private void removerAtributosSessao(HttpSession session) {
        session.removeAttribute(NomeVariaveisSessao.MSG_ERRO);
        session.removeAttribute(NomeVariaveisSessao.MSG_SUCESSO);
        session.removeAttribute(NomeVariaveisSessao.VACINA);
        session.removeAttribute(NomeVariaveisSessao.BAIRRO);
    }
}
