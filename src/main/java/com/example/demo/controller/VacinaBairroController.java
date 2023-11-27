package com.example.demo.controller;

import com.example.demo.domain.Bairro;
import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.services.VacinaBairroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

@Controller
@RequestMapping("/vacinas")
public class VacinaBairroController {

    @Autowired
    private VacinaBairroService service;

    @GetMapping
    public ModelAndView telaCadastroVacina(HttpSession session) throws SQLException {
        ModelAndView mv = new ModelAndView("cadastro_vacina");
        mv.addObject("bairros", service.listarBairros());
        mv.addObject("vacinas", service.listarVacinas());
        Bairro bairro = (Bairro) session.getAttribute("bairroNaSessao");
        mv.addObject("bairroNaSessao", bairro);
        return mv;
    }

    @PostMapping
    public String insert(VacinaBairroDto vacinaBairroDto, HttpSession session) throws SQLException {
        Bairro ultimoBairroSalvo = (Bairro) session.getAttribute("bairroNaSessao");
        if (ultimoBairroSalvo == null || ultimoBairroSalvo.getId() != Long.parseLong(vacinaBairroDto.getBairro())) {
            session.setAttribute("bairroNaSessao", service.buscarBairroPorId(vacinaBairroDto.getBairro()));
        }
        service.insert(vacinaBairroDto);
        return "redirect:/vacinas";
    }
}
