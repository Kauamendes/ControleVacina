package com.example.demo.services;

import com.example.demo.NomeVariaveisSessao;
import com.example.demo.domain.*;
import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.repository.BairroRepository;
import com.example.demo.repository.VacinaBairroRepository;
import com.example.demo.repository.VacinaRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class VacinaBairroService {

    @Autowired
    private VacinaBairroRepository vacinaBairroRepository;

    @Autowired
    private VacinaRepository vacinaRepository;

    @Autowired
    private BairroRepository bairroRepository;

    public Mensagem insert(VacinaBairroDto vacinaBairroDto) {
        String vacina = vacinaBairroDto.getVacina().substring(0, vacinaBairroDto.getVacina().indexOf(","));
        String dosagem = vacinaBairroDto.getVacina().substring(vacinaBairroDto.getVacina().indexOf(",") + 1);

        if(vacinaBairroDto.getDose().isBlank() && Boolean.parseBoolean(dosagem)) {
            return Mensagem.builder().mensagem("Informe a dosagem da vacina!").nomeVariavelSessao(NomeVariaveisSessao.MSG_ERRO).build();
        }

        VacinaBairro vacinaBairro = VacinaBairro.builder()
                .bairroId(Long.valueOf(vacinaBairroDto.getBairro()))
                .vacinaId(Long.valueOf(vacina))
                .dose(vacinaBairroDto.getDose())
                .build();

        vacinaBairroRepository.insert(vacinaBairro);
        return Mensagem.builder().mensagem("Vacina salva com sucesso!!").nomeVariavelSessao(NomeVariaveisSessao.MSG_SALVO).build();
    }

    public void atualizarVacinaEBairroSessao(VacinaBairroDto vacinaBairroDto, Long bairroSessaoId, Long vacinaSessaoId, HttpSession session) {
        Long vacinaSelecionadaId = Long.parseLong(vacinaBairroDto.getVacina().substring(0, vacinaBairroDto.getVacina().indexOf(",")));
        Long bairroSelecionadoId = Long.parseLong(vacinaBairroDto.getBairro());

        if (bairroSessaoId == null || bairroSessaoId.compareTo(bairroSelecionadoId) < 0) {
            session.setAttribute(NomeVariaveisSessao.BAIRRO, bairroSelecionadoId);
        }
        if (vacinaSessaoId == null || vacinaSessaoId.compareTo(vacinaSelecionadaId) < 0) {
            session.setAttribute(NomeVariaveisSessao.VACINA, vacinaSelecionadaId);
        }
    }

    public ModelAndView atualizarModelAndViewComVariaveisSessao(ModelAndView mv, HttpSession session) {
        atualizarMensagensModelAndViewComVariaveisSessao(mv, session);

        Long bairroSessaoId = (Long) session.getAttribute(NomeVariaveisSessao.BAIRRO);
        Long vacinaSessaoId = (Long) session.getAttribute(NomeVariaveisSessao.VACINA);

        if (bairroSessaoId != null) mv.addObject(NomeVariaveisSessao.BAIRRO, bairroSessaoId);
        if (vacinaSessaoId != null) mv.addObject(NomeVariaveisSessao.VACINA, vacinaSessaoId);
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

    public List<Bairro> listarBairros() throws SQLException {
        return bairroRepository.listarBairros();
    }

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

    public Bairro buscarBairroPorNome(String nomeBairro) throws SQLException {
        return bairroRepository.buscarBairroPorNome(nomeBairro);
    }
}
