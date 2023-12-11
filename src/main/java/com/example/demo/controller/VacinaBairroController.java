package com.example.demo.controller;

import com.example.demo.dto.VacinaBairroDto;
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

    private String bairroSelecionadoId;
    private String vacinaSelecionadaId;

    @Autowired
    private VacinaBairroService service;

    @GetMapping
    public ModelAndView telaCadastroVacina(HttpSession session, HttpServletResponse response) throws SQLException, IOException {
        service.verificaCargoSessao(session, response);

        ModelAndView mv = new ModelAndView("cadastro_vacina");
        mv.addObject("bairros", service.listarBairros());
        mv.addObject("vacinas", service.listarVacinas());

        String msgSalvar = (String) session.getAttribute("msgSalvar");
        mv.addObject("msgSalvar", msgSalvar);

        if (bairroSelecionadoId != null) mv.addObject("bairroSelecionadoId", Long.parseLong(bairroSelecionadoId));
        if (vacinaSelecionadaId != null) mv.addObject("vacinaSelecionadoId", Long.parseLong(vacinaSelecionadaId));
        return mv;
    }

    @PostMapping
    public String insert(VacinaBairroDto vacinaBairroDto, HttpSession session) throws SQLException {
        if (bairroSelecionadoId == null || Long.parseLong(bairroSelecionadoId) != Long.parseLong(vacinaBairroDto.getBairro())) {
            bairroSelecionadoId = vacinaBairroDto.getBairro();
        }
        if (vacinaSelecionadaId == null || Long.parseLong(vacinaSelecionadaId) != Long.parseLong(vacinaBairroDto.getVacina())) {
            vacinaSelecionadaId = vacinaBairroDto.getVacina();
        }
        service.insert(vacinaBairroDto);
        session.setAttribute("msgSalvar", "Vacina salva com sucesso!");
        return "redirect:/vacinas";
    }
}
