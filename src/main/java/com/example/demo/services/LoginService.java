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
        ModelAndView mv = new ModelAndView();
        Usuario usuarioLogado = usuarioRepository.findByAccess(login);
        if (usuarioLogado == null) {
            mv.addObject("msgErro", "Usuário não encontrado");
            mv.setViewName("login");
            return mv;
        }
        if (usuarioLogado.getCargo().equals(Usuario.TIP_CARGO_APLICADOR)) {
            session.setAttribute("usuarioLogado", usuarioLogado);
            mv.setViewName("cadastro_vacina");
            return mv;
        }
        mv.setViewName("relatorio");
        return mv;
    }

}
