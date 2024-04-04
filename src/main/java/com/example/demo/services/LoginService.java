package com.example.demo.services;

import com.example.demo.NomeVariaveisSessao;
import com.example.demo.domain.Usuario;
import com.example.demo.dto.AlteracaoSenhaDto;
import com.example.demo.dto.LoginDto;
import com.example.demo.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String findByAccess(LoginDto login, HttpSession session) {
        Usuario usuarioLogado = usuarioRepository.findByLogin(login.getLogin());
  
        if (usuarioLogado == null ||  !passwordEncoder.matches(login.getSenha(), usuarioLogado.getSenha())) {
            session.setAttribute(msgErro, "Usuário ou senha inválidos!");
            return "redirect:/";
        }

        session.setAttribute(NomeVariaveisSessao.CARGO, usuarioLogado.getCargo());
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

    public String saveNewSenha(AlteracaoSenhaDto loginUpdateDto, HttpSession session) {
        if (verificaSeUsuarioAdmin(loginUpdateDto, session)) {
            if (!loginUpdateDto.getSenha_update().equals(loginUpdateDto.getConfirmacaoSenha_update())) {
                session.setAttribute(NomeVariaveisSessao.MSG_ERRO, "senhas diferentes");
                return "redirect:/new_senha";
            }

            Usuario usuario = usuarioRepository.findByLogin(loginUpdateDto.getLogin_update());
            if (usuario == null) {
                session.setAttribute(NomeVariaveisSessao.MSG_ERRO, "usuario não encontrado!");
                return "redirect:/new_senha";
            }

            usuario.setSenha(loginUpdateDto.getSenha_update());
            usuarioRepository.updateUsuario(usuario);
            return "redirect:/";
        }
        session.setAttribute(NomeVariaveisSessao.MSG_SALVO, "Alteração salva com sucesso");
        return "redirect:/new_senha";
    }

    private boolean verificaSeUsuarioAdmin(AlteracaoSenhaDto loginUpdateDto, HttpSession session) {

        Usuario usuarioAdmin = usuarioRepository.findByLogin(loginUpdateDto.getLogin_admin());
        if (usuarioAdmin == null || !passwordEncoder.matches(loginUpdateDto.getSenha_admin(), usuarioAdmin.getSenha()) ) {
            session.setAttribute(msgErro, "usuario admin inválido!");
            return false;
        } else if (!usuarioAdmin.isAdmin()) {
            session.setAttribute(NomeVariaveisSessao.MSG_ERRO, "usuario informado não é admin!");
            return false;
        }
        return true;
    }
}
