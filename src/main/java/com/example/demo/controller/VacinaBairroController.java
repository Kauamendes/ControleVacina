package com.example.demo.controller;

import com.example.demo.NomeVariaveisSessao;
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

    @Autowired
    private VacinaBairroService service;

    @GetMapping
    public ModelAndView telaCadastroVacina(HttpSession session, HttpServletResponse response)
            throws SQLException, IOException {
        service.verificaCargoSessao(session, response);

        ModelAndView mv = new ModelAndView("cadastro_vacina");
        mv.addObject("bairros", service.listarBairros());
        mv.addObject("vacinas", service.listarVacinas());
        mv.addObject("dosagens", DosagemEnum.values());

        String msgSalvar = (String) session.getAttribute(NomeVariaveisSessao.MSG_SALVO);
        Long bairroSelecionadoId = (Long) session.getAttribute(NomeVariaveisSessao.BAIRRO);
        Long vacinaSelecionadaId = (Long) session.getAttribute(NomeVariaveisSessao.VACINA);

        if (msgSalvar != null) mv.addObject(NomeVariaveisSessao.MSG_SALVO, msgSalvar);
        if (bairroSelecionadoId != null) mv.addObject(NomeVariaveisSessao.BAIRRO, bairroSelecionadoId);
        if (vacinaSelecionadaId != null) mv.addObject(NomeVariaveisSessao.VACINA, vacinaSelecionadaId);
        session.removeAttribute(NomeVariaveisSessao.MSG_SALVO);
        return mv;
    }

    @PostMapping
    public String insert(VacinaBairroDto vacinaBairroDto, HttpSession session) {
        Long bairroSelecionadoId = (Long) session.getAttribute(NomeVariaveisSessao.BAIRRO);
        Long vacinaSelecionadaId = (Long) session.getAttribute(NomeVariaveisSessao.VACINA);

        String vacina = vacinaBairroDto.getVacina().substring(0, vacinaBairroDto.getVacina().indexOf(","));
        System.out.println(vacina);

        if (bairroSelecionadoId == null || bairroSelecionadoId != Long.parseLong(vacinaBairroDto.getBairro())) {
            session.setAttribute(NomeVariaveisSessao.BAIRRO, Long.parseLong(vacinaBairroDto.getBairro()));
        }
        if (vacinaSelecionadaId == null || vacinaSelecionadaId != Long.parseLong(vacinaBairroDto.getVacina())) {
            session.setAttribute(NomeVariaveisSessao.VACINA, Long.parseLong(vacinaBairroDto.getVacina()));
        }
        service.insert(vacinaBairroDto);
        session.setAttribute(NomeVariaveisSessao.MSG_SALVO, "Vacina salva com sucesso!");
        return "redirect:/vacinas";
    }
}
