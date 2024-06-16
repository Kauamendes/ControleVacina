package com.example.demo.services.impl;

import com.example.demo.mappers.VacinaBairroMapper;
import com.example.demo.services.BairroService;
import com.example.demo.services.VacinaBairroService;
import com.example.demo.services.VacinaService;
import com.example.demo.utils.NomeVariaveisSessao;
import com.example.demo.domain.*;
import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.enums.CargoEnum;
import com.example.demo.enums.DosagemEnum;
import com.example.demo.repository.BairroRepository;
import com.example.demo.repository.VacinaBairroRepository;
import com.example.demo.repository.VacinaRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Service
public class VacinaBairroServiceImpl implements VacinaBairroService {

    private final VacinaBairroRepository vacinaBairroRepository;

    private final VacinaService vacinaService;

    private final BairroService bairroService;

    private final VacinaBairroMapper vacinaBairroMapper;

    @Autowired
    public VacinaBairroServiceImpl(VacinaBairroRepository vacinaBairroRepository,
                                   VacinaService vacinaService,
                                   BairroService bairroService,
                                   VacinaBairroMapper vacinaBairroMapper) {
        this.vacinaBairroRepository = vacinaBairroRepository;
        this.vacinaService = vacinaService;
        this.bairroService = bairroService;
        this.vacinaBairroMapper = vacinaBairroMapper;
    }

    @CacheEvict(value="ultimasVacinasCadastradasPorUsuario", allEntries=true)
    public Mensagem salvar(VacinaBairroDto vacinaBairroDto) {
        String vacina = vacinaBairroDto.getVacina().substring(0, vacinaBairroDto.getVacina().indexOf(","));
        String dosagem = vacinaBairroDto.getVacina().substring(vacinaBairroDto.getVacina().indexOf(",") + 1);
        vacinaBairroDto.setVacina(vacina);
        vacinaBairroDto.setDose(dosagem);

        if (vacinaBairroDto.getDose().isBlank() && Boolean.parseBoolean(dosagem)) {
            return Mensagem.builder().mensagem("Informe a dosagem da vacina!").nomeVariavelSessao(NomeVariaveisSessao.MSG_ERRO).build();
        }
        if (vacinaBairroDto.getDose().isBlank()) vacinaBairroDto.setDose(DosagemEnum.UNICA.getValor().toString());

        vacinaBairroRepository.save(vacinaBairroMapper.toEntity(vacinaBairroDto));
        return Mensagem.fabricarMensagemSalvo("Vacina");
    }

    public void atualizarVacinaEBairroSessao(VacinaBairroDto vacinaBairroDto, Long vacinaSessaoId, HttpSession session) {
        Long vacinaSelecionadaId = Long.parseLong(vacinaBairroDto.getVacina().substring(0, vacinaBairroDto.getVacina().indexOf(",")));

        if (vacinaSessaoId == null || vacinaSessaoId.compareTo(vacinaSelecionadaId) < 0) {
            session.setAttribute(NomeVariaveisSessao.VACINA, vacinaSelecionadaId);
        }
    }

    public ModelAndView atualizarModelAndViewComVariaveisSessao(ModelAndView mv, HttpSession session) {
        atualizarMensagensModelAndViewComVariaveisSessao(mv, session);
        Long vacinaSessaoId = (Long) session.getAttribute(NomeVariaveisSessao.VACINA);
        CargoEnum cargo = (CargoEnum) session.getAttribute(NomeVariaveisSessao.CARGO);

        if (vacinaSessaoId != null) mv.addObject(NomeVariaveisSessao.VACINA, vacinaSessaoId);
        if (cargo != null) mv.addObject(NomeVariaveisSessao.CARGO, cargo.toString());
        return mv;
    }

    private void atualizarMensagensModelAndViewComVariaveisSessao(ModelAndView mv, HttpSession session) {
        String msgSalvar = (String) session.getAttribute(NomeVariaveisSessao.MSG_SUCESSO);
        String msgErro = (String) session.getAttribute(NomeVariaveisSessao.MSG_ERRO);

        if (msgSalvar != null) mv.addObject(NomeVariaveisSessao.MSG_SUCESSO, msgSalvar);
        if (msgErro != null) mv.addObject(NomeVariaveisSessao.MSG_ERRO, msgErro);

        session.removeAttribute(NomeVariaveisSessao.MSG_SUCESSO);
        session.removeAttribute(NomeVariaveisSessao.MSG_ERRO);
    }

    public void verificaCargoSessao(HttpSession session, HttpServletResponse response) throws IOException {
        CargoEnum cargo = (CargoEnum) session.getAttribute("cargo");
        if (cargo == null) {
            response.sendRedirect("/");
        } else if (cargo.equals(CargoEnum.APLICADOR)) {
            response.sendRedirect("/relatorios");
        }
    }

    @Cacheable("ultimasVacinasCadastradasPorUsuario")
    public List<VacinaBairroDto> listarUltimosCadastradosPorUsuarioLogado(String usuarioLogado) {
        return vacinaBairroRepository.findAllByAplicadorOrderByCreatedDateDesc(usuarioLogado).stream()
                .map(vacinaBairroMapper::toDto)
                .toList();
    }

    @Cacheable("vacinas")
    public VacinaBairroDto buscarPorId(Long id) {
        VacinaBairro vacina = vacinaBairroRepository.findById(id).orElseThrow();
        return vacinaBairroMapper.toDto(vacina);
    }

    @CacheEvict(value="ultimasVacinasCadastradasPorUsuario", allEntries=true)
    public Mensagem editar(VacinaBairroDto vacinaBairroDto) {
        String vacina = vacinaBairroDto.getVacina().substring(0, vacinaBairroDto.getVacina().indexOf(","));
        String dosagem = vacinaBairroDto.getVacina().substring(vacinaBairroDto.getVacina().indexOf(",") + 1);

        vacinaBairroDto.setVacina(vacina);
        vacinaBairroDto.setBairro(vacinaBairroDto.getBairro());

        if(vacinaBairroDto.getDose().isBlank() && Boolean.parseBoolean(dosagem)) {
            return Mensagem.builder().mensagem("Informe a dosagem da vacina!").nomeVariavelSessao(NomeVariaveisSessao.MSG_ERRO).build();
        }
        if (vacinaBairroDto.getDose().isBlank()) vacinaBairroDto.setDose(DosagemEnum.UNICA.getValor().toString());

        vacinaBairroRepository.save(vacinaBairroMapper.toEntity(vacinaBairroDto));
        return Mensagem.fabricarMensagemEditado("Vacina");
    }
}