package com.example.demo.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Bairro;
import com.example.demo.domain.Usuario;
import com.example.demo.domain.Vacina;
import com.example.demo.domain.VacinaBairro;
import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.repository.VacinaBairroRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class VacinaBairroService {

    @Autowired
    private VacinaBairroRepository repository;

    public void insert(VacinaBairroDto vacinaBairroDto) {
        VacinaBairro vacinaBairro = VacinaBairro.builder()
                .bairroId(Long.valueOf(vacinaBairroDto.getBairro()))
                .vacinaId(Long.valueOf(vacinaBairroDto.getVacina()))
                .dose(vacinaBairroDto.getDose())
                .build();
        repository.insert(vacinaBairro);
    }

    public List<Bairro> listarBairros() throws SQLException {
        return repository.listarBairros();
    }

    public List<Vacina> listarVacinas() throws SQLException {
        return repository.listarVacinas();
    }

    public Bairro buscarBairroPorId(String id) throws SQLException {
        return repository.buscarBairroPorId(Long.parseLong(id));
    }

    public void verificaCargoSessao(HttpSession session, HttpServletResponse response) throws IOException {
        String cargo = (String) session.getAttribute("cargo");
        if (cargo.equals(Usuario.TIP_CARGO_GESTOR)) {
            response.sendRedirect("/relatorios");
        }
    }

}
