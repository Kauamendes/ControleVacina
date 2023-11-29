package com.example.demo.controller;

import com.example.demo.dto.RelatorioDto;
import com.example.demo.repository.RelatorioRepository;
import com.example.demo.repository.VacinaBairroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioRepository relatorioRepository;

    @Autowired
    private VacinaBairroRepository vacinaBairroRepository;

    @GetMapping
    public String relatorio() {
        return "relatorio";
    }

    @PostMapping("/buscar")
    public ModelAndView buscar(RelatorioDto relatorioDto) throws SQLException {
        ModelAndView mv = new ModelAndView("relatorio");
        mv.addObject("vacinasBairros", relatorioRepository.buscar(relatorioDto));
        return mv;
    }

}
