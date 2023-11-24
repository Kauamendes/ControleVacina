package com.example.demo.services;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Usuario;
import com.example.demo.dto.LoginDto;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.web.servlet.ModelAndView;

@Service
public class LoginService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public ModelAndView findByAccess(LoginDto login, HttpSession session) {
        Usuario usuarioLogado = usuarioRepository.findByAccess(login);
        if (usuarioLogado == null) {
            ModelAndView mv = new ModelAndView("login");
            mv.addObject("msgErro", "Usuário não encontrado");
            return mv;
        }
        session.setAttribute("usuarioLogado", usuarioLogado);
        return usuarioLogado.isAplicador() ? new ModelAndView("cadastro_vacina") : new ModelAndView("relatorio");
    }

}
