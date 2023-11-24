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
import java.util.Map;

@Repository
public class RelatorioRepository {

    public List<VacinaBairroDto> buscar(RelatorioDto relatorioDto) throws SQLException {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<VacinaBairroDto> vacinas = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder query = new StringBuilder("SELECT vb.ID, vb.DATA_APLICACAO as dataAplicacao, b.NOME AS bairro_nome, v.NOME AS vacina_nome FROM VACINA_BAIRRO vb");
        query.append(" INNER JOIN BAIRRO b ON b.id = vb.BAIRRO_ID");
        query.append(" INNER JOIN VACINA v ON v.id = vb.VACINA_ID");

        StringBuilder whereClause = new StringBuilder();

        if (relatorioDto.getBairro() != null && !relatorioDto.getBairro().equals("")) {
            whereClause.append(" WHERE b.id = ?");
            params.add(Long.parseLong(relatorioDto.getBairro()));
        }

        if (relatorioDto.getDataInicio() != null && !relatorioDto.getBairro().equals("")) {
            if (whereClause.length() == 0) {
                whereClause.append(" WHERE vb.DATA_APLICACAO >= ?");
            } else {
                whereClause.append(" AND vb.DATA_APLICACAO >= ?");
            }
            params.add(Date.valueOf(relatorioDto.getDataInicio()));
        }

        if (relatorioDto.getDataFim() != null && !relatorioDto.getBairro().equals("")) {
            if (whereClause.length() == 0) {
                whereClause.append(" WHERE vb.DATA_APLICACAO <= ?");
            } else {
                whereClause.append(" AND vb.DATA_APLICACAO <= ?");
            }
            params.add(Date.valueOf(relatorioDto.getDataFim()));
        }

        String finalQuery = query.toString() + whereClause.toString();

        try (PreparedStatement stmt = conn.prepareStatement(finalQuery)) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                vacinas.add(VacinaBairroDto.builder()
                        .id(resultSet.getLong("id"))
                        .bairro(resultSet.getString("bairro_nome"))
                        .vacina(resultSet.getString("vacina_nome"))
                        .dataAplicacao(resultSet.getDate("dataAplicacao"))
                        .build());
            }
        } finally {
            conexao.desconectar(conn);
        }

        return vacinas;
    }
}
