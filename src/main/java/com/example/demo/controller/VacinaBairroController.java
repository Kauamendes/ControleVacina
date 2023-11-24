package com.example.demo.controller;

import com.example.demo.domain.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.services.VacinaBairroService;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/vacinas")
public class VacinaBairroController {

    @Autowired
    private VacinaBairroService service;

    @GetMapping
    public ModelAndView telaCadastroVacina(HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado != null && usuarioLogado.isAplicador()) {
            return new ModelAndView("cadastro_vacina");
        }

        ModelAndView mv = new ModelAndView("login");
        String msgErro = usuarioLogado == null ? "Sessão encerrada, por favor faça login novamente" : "Usuário sem acesso";
        mv.addObject("msgErro", msgErro);
        return mv;
    }

    @PostMapping
    public String insert(VacinaBairroDto vacinaBairroDto) {
        service.insert(vacinaBairroDto);
        return "redirect:/vacinas";
    }
}
