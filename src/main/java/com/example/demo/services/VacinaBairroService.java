package com.example.demo.services;

import com.example.demo.NomeVariaveisSessao;
import com.example.demo.domain.*;
import com.example.demo.dto.RelatorioDto;
import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.enums.CargoEnum;
import com.example.demo.enums.DosagemEnum;
import com.example.demo.repository.BairroRepository;
import com.example.demo.repository.VacinaBairroRepository;
import com.example.demo.repository.VacinaRepository;
import com.example.demo.utils.DateUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
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
                .bairro(Bairro.builder().id(Long.parseLong(vacinaBairroDto.getBairro())).build())
                .vacina(Vacina.builder().id(Long.valueOf(vacina)).build())
                .dose(vacinaBairroDto.getDose())
                .aplicador(vacinaBairroDto.getAplicador())
                .observacoes(vacinaBairroDto.getObservacoes())
                .build();
        vacinaBairroRepository.save(vacinaBairro);

        vacinaBairroRepository.save(vacinaBairro);
        return Mensagem.builder().mensagem("Vacina salva com sucesso!!").nomeVariavelSessao(NomeVariaveisSessao.MSG_SALVO).build();
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
        String msgSalvar = (String) session.getAttribute(NomeVariaveisSessao.MSG_SALVO);
        String msgErro = (String) session.getAttribute(NomeVariaveisSessao.MSG_ERRO);

        if (msgSalvar != null) mv.addObject(NomeVariaveisSessao.MSG_SALVO, msgSalvar);
        if (msgErro != null) mv.addObject(NomeVariaveisSessao.MSG_ERRO, msgErro);

        session.removeAttribute(NomeVariaveisSessao.MSG_SALVO);
        session.removeAttribute(NomeVariaveisSessao.MSG_ERRO);
    }

    public List<Bairro> listarBairros() {
        return bairroRepository.findAll();
    }

    public List<Vacina> listarVacinas() {
        return vacinaRepository.findAll();
    }

    public void verificaCargoSessao(HttpSession session, HttpServletResponse response) throws IOException {
        CargoEnum cargo = (CargoEnum) session.getAttribute("cargo");
        if (cargo == null) {
            response.sendRedirect("/");
        } else if (cargo.equals(CargoEnum.APLICADOR)) {
            response.sendRedirect("/relatorios");
        }
    }

    public Bairro buscarBairroPorNome(String nomeBairro) {
        return bairroRepository.findByNome(nomeBairro);
    }

    public List<VacinaBairroDto> buscar(RelatorioDto relatorioDto) {
        Long bairroId = Long.parseLong(relatorioDto.getBairro());
        Long vacinaID = Long.parseLong(relatorioDto.getVacina());
        Timestamp dataInicial = DateUtils.parseStringToTimestamp(relatorioDto.getDataInicio());
        Timestamp dataFinal = DateUtils.parseStringToTimestamp(relatorioDto.getDataFim());
        return vacinaBairroRepository.buscar(bairroId, vacinaID, dataInicial, dataFinal);
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
                .build();

        vacinaBairroRepository.update(vacinaBairro);
        return Mensagem.builder().mensagem("Vacina editada com sucesso!").nomeVariavelSessao(NomeVariaveisSessao.MSG_SALVO).build();
    }
}
