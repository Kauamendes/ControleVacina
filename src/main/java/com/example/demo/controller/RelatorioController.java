package com.example.demo.controller;

import com.example.demo.NomeVariaveisSessao;
import com.example.demo.domain.Usuario;
import com.example.demo.dto.RelatorioDto;
import com.example.demo.repository.BairroRepository;
import com.example.demo.repository.RelatorioRepository;
import com.example.demo.repository.VacinaRepository;
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
    private RelatorioRepository relatorioRepository;

    @Autowired
    private BairroRepository bairroRepository;

    @Autowired
    private VacinaRepository vacinaRepository;

    @GetMapping
    public ModelAndView relatorio(HttpSession session, HttpServletResponse response) throws IOException, SQLException {
        ModelAndView mv = new ModelAndView("relatorio");
        String cargo = (String) session.getAttribute(NomeVariaveisSessao.CARGO);
        if (cargo == null) {
            response.sendRedirect("/");
        } else if (cargo.equals(Usuario.TIP_CARGO_APLICADOR)) {
            response.sendRedirect("/vacinas");
        }
        mv.addObject("bairros", bairroRepository.listarBairros());
        mv.addObject("vacinas", vacinaRepository.listarVacinas());
        return mv;
    }

    @PostMapping("/buscar")
    public ModelAndView buscar(RelatorioDto relatorioDto) throws SQLException {
        ModelAndView mv = new ModelAndView("relatorio");
        mv.addObject("bairros", bairroRepository.listarBairros());
        mv.addObject("vacinas", vacinaRepository.listarVacinas());
        mv.addObject("vacinasBairros", relatorioRepository.buscar(relatorioDto));

        if (!relatorioDto.getBairro().isBlank())
            mv.addObject(NomeVariaveisSessao.BAIRRO, Long.parseLong(relatorioDto.getBairro()));
        if (!relatorioDto.getVacina().isBlank())
            mv.addObject(NomeVariaveisSessao.VACINA, Long.parseLong(relatorioDto.getVacina()));
        if (!relatorioDto.getDataInicio().isBlank())
            mv.addObject(NomeVariaveisSessao.DATA_INICIO, LocalDateTime.parse(relatorioDto.getDataInicio()));
        if (!relatorioDto.getDataFim().isBlank())
            mv.addObject(NomeVariaveisSessao.DATA_FIM, LocalDateTime.parse(relatorioDto.getDataFim()));
        return mv;
    }

    @PostMapping("/listar")
    public ModelAndView listar(RelatorioDto relatorioDto) throws SQLException {
        ModelAndView mv = new ModelAndView("relatorio");
        mv.addObject("bairros", bairroRepository.listarBairros());
        mv.addObject("vacinasBairros", relatorioRepository.buscar(relatorioDto));

        if (!relatorioDto.getBairro().isBlank())
            mv.addObject(NomeVariaveisSessao.BAIRRO, Long.parseLong(relatorioDto.getBairro()));
        if (!relatorioDto.getVacina().isBlank())
            mv.addObject(NomeVariaveisSessao.VACINA, Long.parseLong(relatorioDto.getBairro()));
        if (!relatorioDto.getDataInicio().isBlank())
            mv.addObject(NomeVariaveisSessao.DATA_INICIO, Date.valueOf(relatorioDto.getDataInicio()));
        if (!relatorioDto.getDataFim().isBlank())
            mv.addObject(NomeVariaveisSessao.DATA_FIM, Date.valueOf(relatorioDto.getDataFim()));
        return mv;
    }

}
