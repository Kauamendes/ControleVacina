package com.example.demo.repository;

import com.example.demo.config.Conexao;
import com.example.demo.dto.RelatorioDto;
import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.utils.DateUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RelatorioRepository {

    public List<VacinaBairroDto> buscar(RelatorioDto relatorioDto) throws SQLException {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<VacinaBairroDto> vacinas = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder query = new StringBuilder("SELECT v.NOME AS vacina, b.NOME AS bairro, count(*) as quantidade FROM VACINA_BAIRRO vb");
        query.append(" INNER JOIN BAIRRO b ON b.id = vb.BAIRRO_ID");
        query.append(" INNER JOIN VACINA v ON v.id = vb.VACINA_ID");

        StringBuilder whereClause = new StringBuilder();

        if (relatorioDto.getBairro() != null && !relatorioDto.getBairro().equals("")) {
            whereClause.append(" WHERE b.id = ?");
            params.add(Long.parseLong(relatorioDto.getBairro()));
        }

        if (relatorioDto.getVacina() != null && !relatorioDto.getVacina().equals("")) {
            if (whereClause.isEmpty()) {
                whereClause.append(" WHERE v.id = ?");
            } else {
                whereClause.append(" AND v.id = ?");
            }
            params.add(Long.parseLong(relatorioDto.getVacina()));
        }

        if (relatorioDto.getDataInicio() != null && !relatorioDto.getDataInicio().equals("")) {
            if (whereClause.isEmpty()) {
                whereClause.append(" WHERE vb.DATA_APLICACAO >= ?");
            } else {
                whereClause.append(" AND vb.DATA_APLICACAO >= ?");
            }
            params.add(DateUtils.parseStringToTimestamp(relatorioDto.getDataInicio()));
        }

        if (relatorioDto.getDataFim() != null && !relatorioDto.getDataFim().equals("")) {
            if (whereClause.isEmpty()) {
                whereClause.append(" WHERE vb.DATA_APLICACAO <= ?");
            } else {
                whereClause.append(" AND vb.DATA_APLICACAO <= ?");
            }
            params.add(DateUtils.parseStringToTimestamp(relatorioDto.getDataFim()));
        }

        String finalQuery = query + whereClause.toString();
        finalQuery += " GROUP BY v.NOME, b.NOME ORDER BY v.NOME";

        try (PreparedStatement stmt = conn.prepareStatement(finalQuery)) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                vacinas.add(VacinaBairroDto.builder()
                        .vacina(resultSet.getString("vacina"))
                        .bairro(resultSet.getString("bairro"))
                        .quantidade(resultSet.getInt("quantidade"))
                        .build());
            }
        } finally {
            conexao.desconectar(conn);
        }

        return vacinas;
    }
}
