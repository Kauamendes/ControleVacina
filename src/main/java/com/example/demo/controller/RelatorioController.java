package com.example.demo.controller;

import com.example.demo.domain.Usuario;
import com.example.demo.dto.RelatorioDto;
import com.example.demo.repository.RelatorioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpRequest;
import java.sql.SQLException;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioRepository relatorioRepository;

    @GetMapping("/")
    public String relatorio() {
        return "relatorio";
    }

    @PostMapping("/buscar")
    public ModelAndView listar(RelatorioDto relatorioDto, HttpServletRequest request) throws SQLException {
        ModelAndView mv = new ModelAndView("login");
        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado.isAplicador()) {
            mv.setViewName("relatorio");
            mv.addObject("vacinasBairros", relatorioRepository.buscar(relatorioDto));
            return mv;
        }
        return mv;
    }

}
