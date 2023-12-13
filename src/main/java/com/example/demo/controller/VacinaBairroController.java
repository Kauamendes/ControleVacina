package com.example.demo.controller;

import com.example.demo.domain.Bairro;
import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.enums.DosagemEnum;
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
import java.sql.SQLException;


@Controller
@RequestMapping("/vacinas")
public class VacinaBairroController {

    @Autowired
    private VacinaBairroService service;

    @GetMapping
    public ModelAndView telaCadastroVacina(HttpSession session, HttpServletResponse response) throws SQLException, IOException {
        service.verificaCargoSessao(session, response);

        ModelAndView mv = new ModelAndView("cadastro_vacina");
        mv.addObject("bairros", service.listarBairros());
        mv.addObject("vacinas", service.listarVacinas());
        mv.addObject("dosagens", DosagemEnum.getDoses());
        Bairro bairroNaSessao = (Bairro) session.getAttribute("bairroNaSessao");

        String msgSalvar = (String) session.getAttribute("msgSalvar");
        Long bairroSelecionadoId = (Long) session.getAttribute("bairroSelecionadoId");
        Long vacinaSelecionadaId = (Long) session.getAttribute("vacinaSelecionadaId");

        if (msgSalvar != null) mv.addObject("msgSalvar", msgSalvar);
        if (bairroSelecionadoId != null) mv.addObject("bairroSelecionadoId", bairroSelecionadoId);
        if (vacinaSelecionadaId != null) mv.addObject("vacinaSelecionadoId", vacinaSelecionadaId);
        return mv;
    }

    @PostMapping
    public String insert(VacinaBairroDto vacinaBairroDto, HttpSession session) throws SQLException {
        Long bairroSelecionadoId = (Long) session.getAttribute("bairroSelecionadoId");
        Long vacinaSelecionadaId = (Long) session.getAttribute("vacinaSelecionadaId");

        if (bairroSelecionadoId == null || bairroSelecionadoId != Long.parseLong(vacinaBairroDto.getBairro())) {
            session.setAttribute("bairroSelecionadoId", vacinaBairroDto.getBairro());
        }
        if (vacinaSelecionadaId == null || vacinaSelecionadaId != Long.parseLong(vacinaBairroDto.getVacina())) {
            session.setAttribute("vacinaSelecionadoId", vacinaBairroDto.getVacina());
        }
        service.insert(vacinaBairroDto);
        session.setAttribute("msgSalvar", "Vacina salva com sucesso!");
        return "redirect:/vacinas";
    }
}
