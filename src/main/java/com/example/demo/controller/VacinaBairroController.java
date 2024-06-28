package com.example.demo.controller;

import com.example.demo.services.BairroService;
import com.example.demo.services.VacinaBairroService;
import com.example.demo.services.VacinaService;
import com.example.demo.utils.NomeVariaveisSessao;
import com.example.demo.domain.Mensagem;
import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.enums.DosagemEnum;
import com.example.demo.repository.RelatorioRepository;
import com.example.demo.services.impl.VacinaBairroServiceImpl;
import com.example.demo.utils.Sessao;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

@Controller
@RequestMapping("/vacinas")
public class VacinaBairroController {

    private final VacinaBairroService vacinaBairroService;
    private final BairroService bairroService;
    private final VacinaService vacinaService;

    @Autowired
    public VacinaBairroController(VacinaBairroService vacinaBairroService,
                                  BairroService bairroService,
                                  VacinaService vacinaService) {
        this.vacinaBairroService = vacinaBairroService;
        this.bairroService = bairroService;
        this.vacinaService = vacinaService;
    }

    @GetMapping
    public ModelAndView telaCadastroVacina(HttpSession session, HttpServletResponse response) throws IOException {
        Sessao.verificaCargoSessao(session, response);
        String usuarioLogado = (String) session.getAttribute(NomeVariaveisSessao.USUARIO_LOGADO);

        ModelAndView mv = new ModelAndView("cadastro_vacina");
        mv.addObject("bairros", bairroService.listar());
        mv.addObject("vacinas", vacinaService.listar());
        mv.addObject("dosagens", DosagemEnum.values());
        mv.addObject("vacinasBairro", vacinaBairroService.listarUltimosCadastradosPorUsuario(usuarioLogado));

        return Sessao.atualizarModelAndViewComVariaveisSessao(mv, session);
    }

    @PostMapping
    public String insert(VacinaBairroDto vacinaBairroDto, HttpSession session) {
        Long vacinaSessaoId = (Long) session.getAttribute(NomeVariaveisSessao.VACINA);
        Long bairroSessaoId = (Long) session.getAttribute(NomeVariaveisSessao.BAIRRO);
        String usuarioLogado = (String) session.getAttribute(NomeVariaveisSessao.USUARIO_LOGADO);
        Long editandoId = (Long) session.getAttribute(NomeVariaveisSessao.EDITANDO_ID);

        Sessao.atualizarVacinaEBairroSessao(vacinaBairroDto, vacinaSessaoId, bairroSessaoId, session);
        vacinaBairroDto.setAplicador(usuarioLogado);

        if (Objects.nonNull(editandoId)) {
            vacinaBairroDto.setId(editandoId);
            Mensagem mensagem = vacinaBairroService.atualizar(vacinaBairroDto);
            session.removeAttribute(NomeVariaveisSessao.EDITANDO_ID);
            session.setAttribute(mensagem.getNomeVariavelSessao(), mensagem.getMensagem());
            return "redirect:/vacinas";
        }

        Mensagem mensagem = vacinaBairroService.salvar(vacinaBairroDto);
        session.setAttribute(mensagem.getNomeVariavelSessao(), mensagem.getMensagem());
        return "redirect:/vacinas";
    }

    @GetMapping("/editarTela/{id}")
    public String editarTela(@PathVariable("id") String id, HttpSession session) {
        VacinaBairroDto vacinaSalva = vacinaBairroService.buscarPorId(Long.parseLong(id));
        if (Objects.nonNull(vacinaSalva)) {
            session.setAttribute(NomeVariaveisSessao.EDITANDO_ID, vacinaSalva.getId());
            session.setAttribute(NomeVariaveisSessao.VACINA, Long.parseLong(vacinaSalva.getVacina()));
            session.setAttribute(NomeVariaveisSessao.BAIRRO, Long.parseLong(vacinaSalva.getBairro()));
        }
        return "redirect:/vacinas";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") String id) {
        vacinaBairroService.excluirPorId(Long.parseLong(id));
        return "redirect:/vacinas";
    }
}
