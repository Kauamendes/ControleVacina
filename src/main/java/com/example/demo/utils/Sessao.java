package com.example.demo.utils;

import com.example.demo.domain.Usuario;
import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.enums.CargoEnum;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.experimental.UtilityClass;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Objects;

@UtilityClass
public class Sessao {

    public static void verificaCargoSessao(HttpSession session, HttpServletResponse response) throws IOException {
        CargoEnum cargo = (CargoEnum) session.getAttribute(NomeVariaveisSessao.CARGO);
        if (cargo == null) {
            response.sendRedirect("/");
        } else if (Objects.equals(CargoEnum.APLICADOR, cargo)) {
            response.sendRedirect("/vacinas");
        } else if (Objects.equals(CargoEnum.GESTOR, cargo)) {
            response.sendRedirect("/relatorios");
        }
    }

    public static void atualizarVacinaEBairroSessao(VacinaBairroDto vacinaBairroDto, Long vacinaSessaoId, Long bairroSessaoId, HttpSession session) {
        Long vacinaSelecionadaId = Long.parseLong(vacinaBairroDto.getVacina().substring(0, vacinaBairroDto.getVacina().indexOf(",")));
        Long bairroSelecionadoId = Long.parseLong(vacinaBairroDto.getBairro());

        if (vacinaSessaoId == null || vacinaSessaoId.compareTo(vacinaSelecionadaId) < 0) {
            session.setAttribute(NomeVariaveisSessao.VACINA, vacinaSelecionadaId);
        }

        if (bairroSessaoId == null || bairroSessaoId.compareTo(bairroSelecionadoId) < 0) {
            session.setAttribute(NomeVariaveisSessao.BAIRRO, bairroSelecionadoId);
        }
    }

    public static ModelAndView atualizarModelAndViewComVariaveisSessao(ModelAndView mv, HttpSession session) {
        atualizarMensagensModelAndViewComVariaveisSessao(mv, session);
        return mv;
    }

    private static void atualizarMensagensModelAndViewComVariaveisSessao(ModelAndView mv, HttpSession session) {
        Long vacinaSessaoId = (Long) session.getAttribute(NomeVariaveisSessao.VACINA);
        Long bairroSessaoId = (Long) session.getAttribute(NomeVariaveisSessao.BAIRRO);
        Long editandoId = (Long) session.getAttribute(NomeVariaveisSessao.EDITANDO_ID);
        String cargo = (String) session.getAttribute(NomeVariaveisSessao.CARGO);

        if (vacinaSessaoId != null) mv.addObject(NomeVariaveisSessao.VACINA, vacinaSessaoId);
        if (bairroSessaoId != null) mv.addObject(NomeVariaveisSessao.BAIRRO, bairroSessaoId);
        if (cargo != null) mv.addObject(NomeVariaveisSessao.CARGO, cargo);
        if (editandoId != null) mv.addObject(NomeVariaveisSessao.EDITANDO_ID, cargo);

        String msgSalvar = (String) session.getAttribute(NomeVariaveisSessao.MSG_SUCESSO);
        String msgErro = (String) session.getAttribute(NomeVariaveisSessao.MSG_ERRO);

        if (msgSalvar != null) mv.addObject(NomeVariaveisSessao.MSG_SUCESSO, msgSalvar);
        if (msgErro != null) mv.addObject(NomeVariaveisSessao.MSG_ERRO, msgErro);

        session.removeAttribute(NomeVariaveisSessao.MSG_SUCESSO);
        session.removeAttribute(NomeVariaveisSessao.MSG_ERRO);
    }
}
