package com.example.demo.controller;

import com.example.demo.NomeVariaveisSessao;
import com.example.demo.domain.Bairro;
import com.example.demo.services.VacinaBairroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class GeolocalizacaoController {

    @Autowired
    private VacinaBairroService vacinaBairroService;

    @GetMapping("/geolocalizacao/{nomeBairro}")
    public boolean obterBairro(@PathVariable String nomeBairro, HttpSession session) throws Exception {
        if (nomeBairro.equalsIgnoreCase("undefined")) return false;
        Bairro bairroNaSessao = (Bairro) session.getAttribute(NomeVariaveisSessao.BAIRRO_GEOLOCALIZACAO);
        if (bairroNaSessao == null || !bairroNaSessao.getNome().equalsIgnoreCase(nomeBairro)) {
            Bairro bairro = vacinaBairroService.buscarBairroPorNome(nomeBairro);
            if (bairro == null || bairro.getId() == null) {
                throw new Exception("Bairro não encontrado");
            }
            session.setAttribute(NomeVariaveisSessao.BAIRRO_GEOLOCALIZACAO, bairro);
            session.setAttribute(NomeVariaveisSessao.BAIRRO, bairro.getId());
            return true;
        }
        return false;
    }
}
