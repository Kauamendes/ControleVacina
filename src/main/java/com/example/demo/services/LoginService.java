package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Usuario;
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
        if (usuarioLogado.getCargo().equals(Usuario.TIP_CARGO_APLICADOR)) {
            return "redirect:/vacinas";
        }
        return "redirect:/relatorios";
    }

}
