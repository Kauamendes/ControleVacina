package com.example.demo.services;

import com.example.demo.domain.Bairro;
import com.example.demo.domain.Usuario;
import com.example.demo.domain.Vacina;
import com.example.demo.domain.VacinaBairro;
import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.repository.VacinaBairroRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class VacinaBairroService {

    @Autowired
    private VacinaBairroRepository repository;

    public void insert(VacinaBairroDto vacinaBairroDto) {
        VacinaBairro vacinaBairro = VacinaBairro.builder()
                .bairroId(Long.valueOf(vacinaBairroDto.getBairro()))
                .vacinaId(Long.valueOf(vacinaBairroDto.getVacina()))
                .build();
        repository.insert(vacinaBairro);
    }

    public List<Bairro> listarBairros() throws SQLException {
        return repository.listarBairros();
    }

    public List<Vacina> listarVacinas() throws SQLException {
        return repository.listarVacinas();
    }

    public void verificaCargoSessao(HttpSession session, HttpServletResponse response) throws IOException {
        String cargo = (String) session.getAttribute("cargo");
        if (cargo == null) {
            response.sendRedirect("/");
        }
        else if (cargo.equals(Usuario.TIP_CARGO_GESTOR)) {
            response.sendRedirect("/relatorios");
        }
    }

    public Bairro buscarBairroPorNome(String nomeBairro) throws SQLException {
        return repository.buscarBairroPorNome(nomeBairro);
    }
}
