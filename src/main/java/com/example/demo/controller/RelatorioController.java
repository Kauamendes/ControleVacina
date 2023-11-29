package com.example.demo.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.domain.Usuario;
import com.example.demo.dto.RelatorioDto;
import com.example.demo.repository.RelatorioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioRepository relatorioRepository;

    @GetMapping
    public String relatorio(HttpSession session) {
        String cargo = (String) session.getAttribute("cargo");
        if (cargo.equals(Usuario.TIP_CARGO_APLICADOR)) {
            return "redirect:/vacinas";
        }
        return "relatorio";
    }

    @PostMapping("/buscar")
    public ModelAndView buscar(RelatorioDto relatorioDto) throws SQLException {
        ModelAndView mv = new ModelAndView("relatorio");
        mv.addObject("vacinasBairros", relatorioRepository.buscar(relatorioDto));
        return mv;
    }

}
