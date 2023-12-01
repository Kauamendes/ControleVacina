package com.example.demo.controller;

import com.example.demo.domain.Usuario;
import com.example.demo.dto.RelatorioDto;
import com.example.demo.repository.RelatorioRepository;
import com.example.demo.repository.VacinaBairroRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioRepository relatorioRepository;

    @Autowired
    private VacinaBairroRepository vacinaBairroRepository;

    @GetMapping
    public ModelAndView relatorio(HttpSession session, HttpServletResponse response) throws IOException {
        ModelAndView mv = new ModelAndView("relatorio");
        String cargo = (String) session.getAttribute("cargo");
        if (cargo.equals(Usuario.TIP_CARGO_APLICADOR)) {
            response.sendRedirect("/vacinas");
        }
        mv.addObject("bairros", vacinaBairroRepository.listarBairros());
        return mv;
    }

    @PostMapping("/buscar")
    public ModelAndView buscar(RelatorioDto relatorioDto) throws SQLException {
        ModelAndView mv = new ModelAndView("relatorio");
        mv.addObject("bairros", vacinaBairroRepository.listarBairros());
        mv.addObject("vacinasBairros", relatorioRepository.buscar(relatorioDto));
        return mv;
    }

}
