package com.example.demo.repository;

import com.example.demo.config.Conexao;
import com.example.demo.dto.RelatorioDto;
import com.example.demo.dto.VacinaBairroDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RelatorioRepository {

    public List<VacinaBairroDto> buscar(RelatorioDto relatorioDto) throws SQLException {
        List<VacinaBairroDto> vacinas = new ArrayList<>();

        if (relatorioDto.getBairro() != null) {
            relatorioDto.setBairro(null);
        }

        String query = " SELECT a.id as id_vacina, a.nome as nome_vacina, " +
                    " c.nome as bairro_nome, b.data_aplicacao " +
                    " FROM vacina a " +
                    " INNER JOIN vacina_bairro b ON b.vacina_id = a.id " +
                    " INNER JOIN bairro c ON c.id = b.bairro_id " +
                    " WHERE ("+ relatorioDto.getBairro() +" IS NULL OR "+ relatorioDto.getBairro() +" = c.nome)";

        if (relatorioDto.getDataInicio() != null) {
            query += "AND '" + relatorioDto.getDataInicio() + "' > b.data_aplicacao";
        }
        if (relatorioDto.getDataFim() != null) {
            query += "AND '" + relatorioDto.getDataFim() + "' > b.data_aplicacao";
        }

        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();

        PreparedStatement stmt = null;
        stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = stmt.executeQuery();
            if (resultSet.wasNull()) {
                return new ArrayList<>();
            }
            while (resultSet.next()) {
                    vacinas.add(
                            VacinaBairroDto.builder()
                                    .id(resultSet.getLong("id_vacina"))
                                    .bairro(resultSet.getString("bairro_nome"))
                                    .vacina(resultSet.getString("nome_vacina"))
                                    .build());
            }

            conexao.desconectar(conn);
        return vacinas;
    }

    public int countAllRows(final ResultSet resultSet) throws SQLException {
        int count = 0;
        while (resultSet.next()) {
            count += 1;
        }
        return count+1;
    }
}
