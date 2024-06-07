package com.example.demo.controller;

import com.example.demo.NomeVariaveisSessao;
import com.example.demo.domain.Mensagem;
import com.example.demo.dto.RelatorioDto;
import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.enums.DosagemEnum;
import com.example.demo.repository.RelatorioRepository;
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

    @Autowired
    private RelatorioRepository relatorioRepository;

    @GetMapping
    public ModelAndView telaCadastroVacina(HttpSession session, HttpServletResponse response) throws SQLException, IOException {
        service.verificaCargoSessao(session, response);
        String usuarioLogado = (String) session.getAttribute(NomeVariaveisSessao.USUARIO_LOGADO);
        
        ModelAndView mv = new ModelAndView("cadastro_vacina");
        mv.addObject("bairros", service.listarBairros());
        mv.addObject("vacinas", service.listarVacinas());
        mv.addObject("dosagens", DosagemEnum.values());
        mv.addObject("vacinasBairro", service.listarUltimoCadastradosPorUsuario(usuarioLogado));

        return service.atualizarModelAndViewComVariaveisSessao(mv, session);
    }

    @PostMapping
    public String insert(VacinaBairroDto vacinaBairroDto, HttpSession session) {
        Long vacinaSessaoId = (Long) session.getAttribute(NomeVariaveisSessao.VACINA);
        Long bairroSessaoId = (Long) session.getAttribute(NomeVariaveisSessao.BAIRRO);
        String usuarioLogado = (String) session.getAttribute(NomeVariaveisSessao.USUARIO_LOGADO);

        service.atualizarVacinaEBairroSessao(vacinaBairroDto, vacinaSessaoId, bairroSessaoId, session);

        if (vacinaBairroDto.getDose().isBlank()) vacinaBairroDto.setDose(DosagemEnum.UNICA.getValor().toString());
        vacinaBairroDto.setAplicador(usuarioLogado);

        Mensagem mensagem = service.insert(vacinaBairroDto);
        session.setAttribute(mensagem.getNomeVariavelSessao(), mensagem.getMensagem());
        return "redirect:/vacinas";
    }
}
