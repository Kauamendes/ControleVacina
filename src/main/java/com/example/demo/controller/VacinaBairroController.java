package com.example.demo.controller;

import com.example.demo.domain.Bairro;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.services.VacinaBairroService;
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
        mv.addObject("bairros", service.listarBairros ());
        mv.addObject("vacinas", service.listarVacinas());
        String msgSalvar = (String) session.getAttribute("msgSalvar");
        mv.addObject("msgSalvar", msgSalvar);
        return mv;
    }

    @PostMapping
    public String insert(VacinaBairroDto vacinaBairroDto, HttpSession session) throws SQLException {
        Bairro ultimoBairroSalvo = (Bairro) session.getAttribute("ultimoBairroSalvo");
        if (ultimoBairroSalvo == null || ultimoBairroSalvo.getId() !=  Long.parseLong(vacinaBairroDto.getBairro())) {
            session.setAttribute("ultimoBairroSalvo", service.buscarBairroPorId(vacinaBairroDto.getBairro()));
        }
        service.insert(vacinaBairroDto);
        session.setAttribute("msgSalvar", "Vacina salva com sucesso!");
        return "redirect:/vacinas";
    }
}
