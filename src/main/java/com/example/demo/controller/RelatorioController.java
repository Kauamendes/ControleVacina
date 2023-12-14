package com.example.demo.controller;

import com.example.demo.domain.Usuario;
import com.example.demo.dto.RelatorioDto;
import com.example.demo.repository.BairroRepository;
import com.example.demo.repository.RelatorioRepository;
import com.example.demo.repository.VacinaBairroRepository;
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

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioRepository relatorioRepository;

    @Autowired
    private BairroRepository bairroRepository;

    @GetMapping
    public ModelAndView relatorio(HttpSession session, HttpServletResponse response) throws IOException, SQLException {
        ModelAndView mv = new ModelAndView("relatorio");
        String cargo = (String) session.getAttribute("cargo");
        if (cargo.equals(Usuario.TIP_CARGO_APLICADOR)) {
            response.sendRedirect("/vacinas");
        }
        mv.addObject("bairros", bairroRepository.listarBairros());
        return mv;
    }

    @PostMapping("/buscar")
    public ModelAndView buscar(RelatorioDto relatorioDto, HttpSession session) throws SQLException {
        String bairro = relatorioDto.getBairro();
        String dataInicio = relatorioDto.getDataInicio();
        String dataFim = relatorioDto.getDataFim();

        ModelAndView mv = new ModelAndView("relatorio");
        mv.addObject("bairros", bairroRepository.listarBairros());
        mv.addObject("vacinasBairros", relatorioRepository.buscar(relatorioDto));

        if (!bairro.equalsIgnoreCase("")) mv.addObject("bairroSelecionadoId", Long.parseLong(bairro));
        if (!dataInicio.equalsIgnoreCase("")) mv.addObject("dataInicio", Date.valueOf(dataInicio));
        if (!dataInicio.equalsIgnoreCase("")) mv.addObject("dataFim", Date.valueOf(dataFim));
        return mv;
    }

}
