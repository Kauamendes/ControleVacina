package com.example.demo.services;

import com.example.demo.domain.Usuario;
import com.example.demo.dto.LoginDto;
import jakarta.servlet.http.HttpSession;

public interface LoginService {

    Usuario buscarPorLogin(String login);
    String logar(LoginDto login, HttpSession session);
}
