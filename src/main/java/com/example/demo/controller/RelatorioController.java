package com.example.demo.controller;

import com.example.demo.repository.RelatorioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    private RelatorioRepository relatorioRepository;

    @GetMapping("/")
    public String paginaInicial() {
        return "relatorio";
    }

//    @GetMapping("/buscar")
//    public ModelAndView buscar(RelatorioDto relatorioDto) throws SQLException {
//        ModelAndView mv = new ModelAndView("relatorio");
//        mv.addObject("vacinaBairro", relatorioRepository.buscar(relatorioDto));
//        return mv;
//    }

}
