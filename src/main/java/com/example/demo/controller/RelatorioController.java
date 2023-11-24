package com.example.demo.controller;

import com.example.demo.domain.Usuario;
import com.example.demo.dto.RelatorioDto;
import com.example.demo.repository.RelatorioRepository;
import com.example.demo.repository.VacinaBairroRepository;
import jakarta.servlet.http.HttpSession;
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
    public ModelAndView relatorio(HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado != null && usuarioLogado.isGestor()) {
            return new ModelAndView("relatorio", "bairros", vacinaBairroRepository.listarBairros());
        }

        ModelAndView mv = new ModelAndView("login");
        String msgErro = usuarioLogado == null ? "Sessão encerrada, por favor faça login novamente" : "Usuário sem acesso";
        mv.addObject("msgErro", msgErro);
        return mv;
    }

    @PostMapping("/buscar")
    public ModelAndView buscar(RelatorioDto relatorioDto) throws SQLException {
        ModelAndView mv = new ModelAndView("relatorio");
        mv.addObject("vacinasBairros", relatorioRepository.buscar(relatorioDto));
        return mv;
    }

}
