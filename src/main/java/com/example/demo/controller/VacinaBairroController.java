package com.example.demo.controller;

import com.example.demo.domain.Bairro;
import com.example.demo.domain.Usuario;
import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.services.VacinaBairroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
            ModelAndView mv = new ModelAndView("cadastro_vacina");
            mv.addObject("bairros", service.listarBairro());
            mv.addObject("vacinas", service.listarVacinas());
            return mv;
        }

        String msgErro = usuarioLogado == null ? "Sessão encerrada, por favor faça login novamente" : "Usuário sem acesso";
        session.setAttribute("msgErro", msgErro);
        return new ModelAndView("login");
    }

    @PostMapping
    public String insert(VacinaBairroDto vacinaBairroDto, HttpSession session) {
        if (vacinaBairroDto.getBairro().equals("0")) {
            String bairroId = (String) session.getAttribute("bairro");
            vacinaBairroDto.setBairro(bairroId);
        }
        service.insert(vacinaBairroDto);
        return "redirect:/vacinas";
    }
}
