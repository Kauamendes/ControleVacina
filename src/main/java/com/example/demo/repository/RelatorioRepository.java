package com.example.demo.repository;

import com.example.demo.config.Conexao;
import com.example.demo.dto.RelatorioDto;
import com.example.demo.dto.VacinaBairroDto;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RelatorioRepository {
    public List<VacinaBairroDto> buscar(RelatorioDto relatorioDto) {
        List<VacinaBairroDto> vacinas = new ArrayList<>();
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();

        String query = " SELECT a.id as id_vacina, a.nome as nome_vacina, " +
                " c.nome as bairro_nome, b.data_aplicacao " +
                " FROM vacina a " +
                " INNER JOIN vacina_bairro b ON b.vacina_id = a.id " +
                " INNER JOIN bairro c ON c.id = b.bairro_id ";


        return vacinas;
    }
}
