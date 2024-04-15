package com.example.demo.services;

import com.example.demo.NomeVariaveisSessao;
import com.example.demo.domain.Usuario;
import com.example.demo.dto.AlteracaoSenhaDto;
import com.example.demo.dto.LoginDto;
import com.example.demo.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String findByAccess(LoginDto login, HttpSession session) {
        Usuario usuarioLogado = usuarioRepository.findByAccess(login);
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

    private void removerAtributosSessao(HttpSession session) {
        session.removeAttribute(NomeVariaveisSessao.MSG_ERRO);
        session.removeAttribute(NomeVariaveisSessao.MSG_SALVO);
        session.removeAttribute(NomeVariaveisSessao.VACINA);
        session.removeAttribute(NomeVariaveisSessao.BAIRRO);
    }
}
