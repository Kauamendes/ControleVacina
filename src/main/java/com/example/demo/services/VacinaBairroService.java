package com.example.demo.services;

import com.example.demo.NomeVariaveisSessao;
import com.example.demo.domain.*;
import com.example.demo.dto.VacinaBairroDto;
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
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Service
public class VacinaBairroService {

    @Autowired
    private VacinaBairroRepository vacinaBairroRepository;

    @Autowired
    private VacinaRepository vacinaRepository;

    @Autowired
    private BairroRepository bairroRepository;

    @CacheEvict(value="ultimasVacinasCadastradasPorUsuario", allEntries=true)
    public Mensagem insert(VacinaBairroDto vacinaBairroDto) {
        String vacina = vacinaBairroDto.getVacina().substring(0, vacinaBairroDto.getVacina().indexOf(","));
        String dosagem = vacinaBairroDto.getVacina().substring(vacinaBairroDto.getVacina().indexOf(",") + 1);

        if(vacinaBairroDto.getDose().isBlank() && Boolean.parseBoolean(dosagem)) {
            return Mensagem.builder().mensagem("Informe a dosagem da vacina!").nomeVariavelSessao(NomeVariaveisSessao.MSG_ERRO).build();
        }
        if (vacinaBairroDto.getDose().isBlank()) vacinaBairroDto.setDose(DosagemEnum.UNICA.getValor().toString());

        VacinaBairro vacinaBairro = VacinaBairro.builder()
                .bairroId(Long.valueOf(vacinaBairroDto.getBairro()))
                .vacinaId(Long.valueOf(vacina))
                .dose(vacinaBairroDto.getDose())
                .aplicador(vacinaBairroDto.getAplicador())
                .observacoes(vacinaBairroDto.getObservacoes())
                .build();

        if (Objects.nonNull(vacinaBairroDto.getQuantidade()) && vacinaBairroDto.getQuantidade() > 1) {
            for(int i = 0; i < vacinaBairroDto.getQuantidade(); i++) {
                vacinaBairroRepository.insert(vacinaBairro);
            }
            return Mensagem.builder().mensagem("Vacinas salvas com sucesso!").nomeVariavelSessao(NomeVariaveisSessao.MSG_SALVO).build();
        }

        vacinaBairroRepository.insert(vacinaBairro);
        return Mensagem.builder().mensagem("Vacina salva com sucesso!").nomeVariavelSessao(NomeVariaveisSessao.MSG_SALVO).build();
    }

    public void atualizarVacinaEBairroSessao(VacinaBairroDto vacinaBairroDto, Long vacinaSessaoId, Long bairroSessaoId, HttpSession session) {
        Long vacinaSelecionadaId = Long.parseLong(vacinaBairroDto.getVacina().substring(0, vacinaBairroDto.getVacina().indexOf(",")));
        Long bairroSelecionadoId = Long.parseLong(vacinaBairroDto.getBairro());

        if (vacinaSessaoId == null || vacinaSessaoId.compareTo(vacinaSelecionadaId) < 0) {
            session.setAttribute(NomeVariaveisSessao.VACINA, vacinaSelecionadaId);
        }

        if (bairroSessaoId == null || bairroSessaoId.compareTo(bairroSelecionadoId) < 0) {
            session.setAttribute(NomeVariaveisSessao.BAIRRO, bairroSelecionadoId);
        }
    }

    public ModelAndView atualizarModelAndViewComVariaveisSessao(ModelAndView mv, HttpSession session) {
        atualizarMensagensModelAndViewComVariaveisSessao(mv, session);
        return mv;
    }

    private void atualizarMensagensModelAndViewComVariaveisSessao(ModelAndView mv, HttpSession session) {
        Long vacinaSessaoId = (Long) session.getAttribute(NomeVariaveisSessao.VACINA);
        Long bairroSessaoId = (Long) session.getAttribute(NomeVariaveisSessao.BAIRRO);
        Long editandoId = (Long) session.getAttribute(NomeVariaveisSessao.EDITANDO_ID);
        String cargo = (String) session.getAttribute(NomeVariaveisSessao.CARGO);
        String observacoes = (String) session.getAttribute(NomeVariaveisSessao.OBSERVACOES);

        if (vacinaSessaoId != null) mv.addObject(NomeVariaveisSessao.VACINA, vacinaSessaoId);
        if (bairroSessaoId != null) mv.addObject(NomeVariaveisSessao.BAIRRO, bairroSessaoId);
        if (cargo != null) mv.addObject(NomeVariaveisSessao.CARGO, cargo);
        if (editandoId != null) mv.addObject(NomeVariaveisSessao.EDITANDO_ID, cargo);
        if (observacoes != null) mv.addObject(NomeVariaveisSessao.OBSERVACOES, observacoes);

        String msgSalvar = (String) session.getAttribute(NomeVariaveisSessao.MSG_SALVO);
        String msgErro = (String) session.getAttribute(NomeVariaveisSessao.MSG_ERRO);

        if (msgSalvar != null) mv.addObject(NomeVariaveisSessao.MSG_SALVO, msgSalvar);
        if (msgErro != null) mv.addObject(NomeVariaveisSessao.MSG_ERRO, msgErro);

        session.removeAttribute(NomeVariaveisSessao.MSG_SALVO);
        session.removeAttribute(NomeVariaveisSessao.MSG_ERRO);
    }

    @Cacheable("bairros")
    public List<Bairro> listarBairros() throws SQLException {
        return bairroRepository.listarBairros();
    }

    @Cacheable("vacinas")
    public List<Vacina> listarVacinas() throws SQLException {
        return vacinaRepository.listarVacinas();
    }

    public void verificaCargoSessao(HttpSession session, HttpServletResponse response) throws IOException {
        String cargo = (String) session.getAttribute("cargo");
        if (cargo == null) {
            response.sendRedirect("/");
        } else if (cargo.equals(Usuario.TIP_CARGO_GESTOR)) {
            response.sendRedirect("/relatorios");
        }
    }

    @Cacheable("bairros")
    public Bairro buscarBairroPorNome(String nomeBairro) throws SQLException {
        return bairroRepository.buscarBairroPorNome(nomeBairro);
    }

    @Cacheable("ultimasVacinasCadastradasPorUsuario")
    public List<VacinaBairroDto> listarUltimosCadastradosPorUsuario(String usuarioLogado) {
        return vacinaBairroRepository.listarUltimosPorUsuario(usuarioLogado);
    }

    @Cacheable("vacinas")
    public VacinaBairroDto buscarVacinaPorId(Long id) {
        return vacinaBairroRepository.buscarVacinaBairroPorId(id);
    }

    @CacheEvict(value="ultimasVacinasCadastradasPorUsuario", allEntries=true)
    public Mensagem editar(VacinaBairroDto vacinaBairroDto) {
        String vacina = vacinaBairroDto.getVacina().substring(0, vacinaBairroDto.getVacina().indexOf(","));
        String dosagem = vacinaBairroDto.getVacina().substring(vacinaBairroDto.getVacina().indexOf(",") + 1);

        if(vacinaBairroDto.getDose().isBlank() && Boolean.parseBoolean(dosagem)) {
            return Mensagem.builder().mensagem("Informe a dosagem da vacina!").nomeVariavelSessao(NomeVariaveisSessao.MSG_ERRO).build();
        }
        if (vacinaBairroDto.getDose().isBlank()) vacinaBairroDto.setDose(DosagemEnum.UNICA.getValor().toString());

        VacinaBairro vacinaBairro = VacinaBairro.builder()
                .id(vacinaBairroDto.getId())
                .bairroId(Long.valueOf(vacinaBairroDto.getBairro()))
                .vacinaId(Long.valueOf(vacina))
                .dose(vacinaBairroDto.getDose())
                .aplicador(vacinaBairroDto.getAplicador())
                .observacoes(vacinaBairroDto.getObservacoes())
                .build();

        vacinaBairroRepository.update(vacinaBairro);
        return Mensagem.builder().mensagem("Vacina editada com sucesso!").nomeVariavelSessao(NomeVariaveisSessao.MSG_SALVO).build();
    }

    @CacheEvict(value="ultimasVacinasCadastradasPorUsuario", allEntries=true)
    public void excluirPorId(Long id) {
        vacinaBairroRepository.excluirPorId(id);
    }
}
