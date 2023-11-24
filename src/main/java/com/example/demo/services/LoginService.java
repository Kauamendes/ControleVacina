package com.example.demo.services;

import com.example.demo.domain.Usuario;
import com.example.demo.dto.LoginDto;
import com.example.demo.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class LoginService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public String findByAccess(LoginDto login, HttpSession session) {
        Usuario usuarioLogado = usuarioRepository.findByAccess(login);
        if (usuarioLogado == null) {
            ModelAndView mv = new ModelAndView("login");
            session.setAttribute("msgErro", "Usuário não encontrado");
            return "redirect:/";
        }
        session.setAttribute("usuarioLogado", usuarioLogado);
        return usuarioLogado.isAplicador() ? "redirect:/vacinas" : "redirect:/relatorios";
    }

}
