package com.example.demo.services;

import com.example.demo.domain.Bairro;
import com.example.demo.domain.Usuario;
import com.example.demo.domain.Vacina;
import com.example.demo.domain.VacinaBairro;
import com.example.demo.dto.RelatorioDto;
import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.enums.CargoEnum;
import com.example.demo.repository.BairroRepository;
import com.example.demo.repository.VacinaBairroRepository;
import com.example.demo.repository.VacinaRepository;
import com.example.demo.utils.DateUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Service
public class VacinaBairroService {

    @Autowired
    private VacinaBairroRepository vacinaBairroRepository;

    @Autowired
    private VacinaRepository vacinaRepository;

    @Autowired
    private BairroRepository bairroRepository;

    public void insert(VacinaBairroDto vacinaBairroDto) {
        String vacina = vacinaBairroDto.getVacina().substring(0, vacinaBairroDto.getVacina().indexOf(","));

        VacinaBairro vacinaBairro = VacinaBairro.builder()
                .bairro(Bairro.builder().id(Long.parseLong(vacinaBairroDto.getBairro())).build())
                .vacina(Vacina.builder().id(Long.valueOf(vacina)).build())
                .dose(vacinaBairroDto.getDose())
                .build();
        vacinaBairroRepository.save(vacinaBairro);
    }

    public List<Bairro> listarBairros() {
        return bairroRepository.findAll();
    }

    public List<Vacina> listarVacinas() {
        return vacinaRepository.findAll();
    }

    public void verificaCargoSessao(HttpSession session, HttpServletResponse response) throws IOException {
        String cargo = (String) session.getAttribute("cargo");
        if (cargo == null) {
            response.sendRedirect("/");
        } else if (cargo.equals(CargoEnum.APLICADOR.getNome())) {
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
    }
}
