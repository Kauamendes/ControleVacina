package com.example.demo.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.domain.Bairro;
import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.enums.DosagemEnum;
import com.example.demo.services.VacinaBairroService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/vacinas")
public class VacinaBairroController {

    @Autowired
    private VacinaBairroService service;

    @GetMapping
    public ModelAndView telaCadastroVacina(HttpSession session, HttpServletResponse response)
            throws SQLException, IOException {
        service.verificaCargoSessao(session, response);

        ModelAndView mv = new ModelAndView("cadastro_vacina");
        mv.addObject("bairros", service.listarBairros());
        mv.addObject("vacinas", service.listarVacinas());
        mv.addObject("dosagens", DosagemEnum.getDoses());
        Bairro bairroNaSessao = (Bairro) session.getAttribute("bairroNaSessao");
        String msgSalvar = (String) session.getAttribute("msgSalvar");
        mv.addObject("msgSalvar", msgSalvar);
        mv.addObject("bairroNaSessao", bairroNaSessao);
        return mv;
    }

    @PostMapping
    public String insert(VacinaBairroDto vacinaBairroDto, HttpSession session) throws SQLException {
        Bairro ultimoBairroSalvo = (Bairro) session.getAttribute("bairroNaSessao");
        if (ultimoBairroSalvo == null || ultimoBairroSalvo.getId() != Long.parseLong(vacinaBairroDto.getBairro())) {
            session.setAttribute("bairroNaSessao", service.buscarBairroPorId(vacinaBairroDto.getBairro()));
        }
        service.insert(vacinaBairroDto);
        session.setAttribute("msgSalvar", "Vacina salva com sucesso!");
        return "redirect:/vacinas";
    }
}
