package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Usuario;
import com.example.demo.dto.AlteracaoSenhaDto;
import com.example.demo.dto.LoginDto;
import com.example.demo.repository.UsuarioRepository;

@Service
public class LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String findByAccess(LoginDto login) {
        Usuario usuarioLogado = usuarioRepository.findByAccess(login);
        if (usuarioLogado == null) {
            return "redirect:/";
        }
        if (usuarioLogado.isAplicador()) {
            return "redirect:/vacinas";
        }
        return "redirect:/relatorios";
    }

    public String saveNewSenha(AlteracaoSenhaDto loginUpdateDto) {
        if (verificaSeUsuarioAdmin(loginUpdateDto)) {
            if (!loginUpdateDto.getSenha_update().equals(loginUpdateDto.getConfirmacaoSenha_update())) {
                System.out.println("senhas diferentes");
                return "redirect:/new_senha";
            }

            Usuario usuario = usuarioRepository.findByLogin(loginUpdateDto.getLogin_update());
            if (usuario == null) {
                System.out.println("usuario não encontrado no banco");
                return "redirect:/new_senha";
            }

            usuario.setSenha(loginUpdateDto.getSenha_update());
            usuarioRepository.updateUsuario(usuario);
            return "redirect:/";
        }
        return "redirect:/new_senha";
    }

    private boolean verificaSeUsuarioAdmin(AlteracaoSenhaDto loginUpdateDto) {
        Usuario usuarioAdmin = usuarioRepository
                .findByAccess(new LoginDto(loginUpdateDto.getLogin_admin(), loginUpdateDto.getSenha_admin()));
        if (usuarioAdmin == null) {
            System.out.println("usuario admin inválido");
            return false;
        } else if (usuarioAdmin != null && !usuarioAdmin.isAdmin()) {
            System.out.println("usuario informado não é admin");
            return false;
        }
        return true;
    }
}
