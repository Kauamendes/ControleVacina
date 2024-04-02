package com.example.demo.controller;

import com.example.demo.NomeVariaveisSessao;
import com.example.demo.domain.Usuario;
import com.example.demo.dto.RelatorioDto;
import com.example.demo.enums.CargoEnum;
import com.example.demo.repository.BairroRepository;
import com.example.demo.repository.VacinaBairroRepository;
import com.example.demo.repository.VacinaRepository;
import com.example.demo.services.VacinaBairroService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private VacinaBairroService vacinaBairroService;

    @GetMapping
    public ModelAndView relatorio(HttpSession session, HttpServletResponse response) throws IOException {
        ModelAndView mv = new ModelAndView("relatorio");
        String cargo = (String) session.getAttribute(NomeVariaveisSessao.CARGO);
        if (cargo == null) {
            response.sendRedirect("/");
        } else if (cargo.equals(CargoEnum.APLICADOR.getNome())) {
            response.sendRedirect("/vacinas");
        }
        mv.addObject("bairros", vacinaBairroService.listarBairros());
        mv.addObject("vacinas", vacinaBairroService.listarVacinas());
        return mv;
    }

    @PostMapping("/buscar")
    public ModelAndView buscar(RelatorioDto relatorioDto) {
        ModelAndView mv = new ModelAndView("relatorio");
        mv.addObject("bairros", vacinaBairroService.listarBairros());
        mv.addObject("vacinas", vacinaBairroService.listarVacinas());
        mv.addObject("vacinasBairros", vacinaBairroService.buscar(relatorioDto));

        if (!relatorioDto.getBairro().isBlank())
            mv.addObject(NomeVariaveisSessao.BAIRRO, Long.parseLong(relatorioDto.getBairro()));
        if (!relatorioDto.getVacina().isBlank())
            mv.addObject(NomeVariaveisSessao.VACINA, Long.parseLong(relatorioDto.getVacina()));
        if (!relatorioDto.getDataInicio().isBlank())
            mv.addObject("dataInicio", LocalDateTime.parse(relatorioDto.getDataInicio()));
        if (!relatorioDto.getDataFim().isBlank())
            mv.addObject("dataFim", LocalDateTime.parse(relatorioDto.getDataFim()));
        return mv;
    }

    @PostMapping("/limpar-campos")
    public String limparCampos(HttpSession session) {
       session.removeAttribute(NomeVariaveisSessao.VACINA);
       session.removeAttribute(NomeVariaveisSessao.BAIRRO);
       return "relatorio";
    }

    @PostMapping("/listar")
    public ModelAndView listar(RelatorioDto relatorioDto) {
        ModelAndView mv = new ModelAndView("relatorio");
        mv.addObject("bairros", vacinaBairroService.listarBairros());
        mv.addObject("vacinasBairros", vacinaBairroService.buscar(relatorioDto));

        if (!relatorioDto.getBairro().isBlank())
            mv.addObject("bairroSelecionadoId", Long.parseLong(relatorioDto.getBairro()));
        if (!relatorioDto.getDataInicio().isBlank())
            mv.addObject("dataInicio", Date.valueOf(relatorioDto.getDataInicio()));
        if (!relatorioDto.getDataFim().isBlank())
            mv.addObject("dataFim", Date.valueOf(relatorioDto.getDataFim()));
        return mv;
    }

}
