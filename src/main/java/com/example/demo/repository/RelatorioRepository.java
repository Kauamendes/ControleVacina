package com.example.demo.repository;

import com.example.demo.config.Conexao;
import com.example.demo.dto.RelatorioDto;
import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.enums.DosagemEnum;
import com.example.demo.utils.DateUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class RelatorioRepository {

    public List<VacinaBairroDto> buscar(RelatorioDto relatorioDto) throws SQLException {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<VacinaBairroDto> vacinas = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder query = new StringBuilder("SELECT v.NOME AS vacina, b.NOME AS bairro, vb.dose as dose, count(*) as quantidade FROM VACINA_BAIRRO vb");
        query.append(" INNER JOIN BAIRRO b ON b.id = vb.BAIRRO_ID");
        query.append(" INNER JOIN VACINA v ON v.id = vb.VACINA_ID");

        StringBuilder whereClause = new StringBuilder();

        if (relatorioDto.getBairro() != null && !relatorioDto.getBairro().isEmpty()) {
            whereClause.append(" WHERE b.id = ?");
            params.add(Long.parseLong(relatorioDto.getBairro()));
        }

        if (relatorioDto.getVacina() != null && !relatorioDto.getVacina().isEmpty()) {
            if (whereClause.isEmpty()) {
                whereClause.append(" WHERE v.id = ?");
            } else {
                whereClause.append(" AND v.id = ?");
            }
            params.add(Long.parseLong(relatorioDto.getVacina()));
        }

        if (relatorioDto.getDataInicio() != null && !relatorioDto.getDataInicio().isEmpty()) {
            if (whereClause.isEmpty()) {
                whereClause.append(" WHERE vb.DATA_APLICACAO >= ?");
            } else {
                whereClause.append(" AND vb.DATA_APLICACAO >= ?");
            }
            params.add(DateUtils.parseStringToTimestamp(relatorioDto.getDataInicio()));
        }

        if (relatorioDto.getDataFim() != null && !relatorioDto.getDataFim().isEmpty()) {
            if (whereClause.isEmpty()) {
                whereClause.append(" WHERE vb.DATA_APLICACAO <= ?");
            } else {
                whereClause.append(" AND vb.DATA_APLICACAO <= ?");
            }
            params.add(DateUtils.parseStringToTimestamp(relatorioDto.getDataFim()));
        }

        if (relatorioDto.getDosagem() != null && !relatorioDto.getDosagem().isEmpty()) {
            if (whereClause.isEmpty()) {
                whereClause.append(" WHERE vb.DOSE = ?");
            } else {
                whereClause.append(" AND vb.DOSE = ?");
            }
            params.add(relatorioDto.getDosagem());
        }

        String finalQuery = query + whereClause.toString();
        finalQuery += " GROUP BY v.NOME, b.NOME, vb.dose ORDER BY v.NOME";

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
                        .dose(resultSet.getString("dose"))
                        .build());
            }
        } finally {
            conexao.desconectar(conn);
        }

        return vacinas;
    }

    public List<VacinaBairroDto> listar(RelatorioDto relatorioDto) throws SQLException {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<VacinaBairroDto> vacinas = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder query = new StringBuilder("SELECT vb.id as id, v.NOME AS vacina, b.NOME AS bairro, vb.aplicador as aplicador, vb.dose as dose, vb.data_aplicacao as data, vb.observacoes as obs FROM VACINA_BAIRRO vb ");
        query.append(" INNER JOIN BAIRRO b ON b.id = vb.BAIRRO_ID ");
        query.append(" INNER JOIN VACINA v ON v.id = vb.VACINA_ID ");

        StringBuilder whereClause = new StringBuilder();

        if (relatorioDto.getBairro() != null && !relatorioDto.getBairro().isEmpty()) {
            whereClause.append(" WHERE b.id = ?");
            params.add(Long.parseLong(relatorioDto.getBairro()));
        }

        if (relatorioDto.getVacina() != null && !relatorioDto.getVacina().isEmpty()) {
            if (whereClause.isEmpty()) {
                whereClause.append(" WHERE v.id = ?");
            } else {
                whereClause.append(" AND v.id = ?");
            }
            params.add(Long.parseLong(relatorioDto.getVacina()));
        }

        if (relatorioDto.getDataInicio() != null && !relatorioDto.getDataInicio().isEmpty()) {
            if (whereClause.isEmpty()) {
                whereClause.append(" WHERE vb.DATA_APLICACAO >= ?");
            } else {
                whereClause.append(" AND vb.DATA_APLICACAO >= ?");
            }
            params.add(DateUtils.parseStringToTimestamp(relatorioDto.getDataInicio()));
        }

        if (relatorioDto.getDataFim() != null && !relatorioDto.getDataFim().isEmpty()) {
            if (whereClause.isEmpty()) {
                whereClause.append(" WHERE vb.DATA_APLICACAO <= ? ");
            } else {
                whereClause.append(" AND vb.DATA_APLICACAO <= ? ");
            }
            params.add(DateUtils.parseStringToTimestamp(relatorioDto.getDataFim()));
        }

        if (relatorioDto.getDosagem() != null && !relatorioDto.getDosagem().isEmpty()) {
            if (whereClause.isEmpty()) {
                whereClause.append(" WHERE vb.DOSE = ?");
            } else {
                whereClause.append(" AND vb.DOSE = ?");
            }
            params.add(relatorioDto.getDosagem());
        }

        String finalQuery = query + whereClause.toString();
        finalQuery += " ORDER BY vb.data_aplicacao DESC ";

        try (PreparedStatement stmt = conn.prepareStatement(finalQuery)) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                vacinas.add(VacinaBairroDto.builder()
                        .id(resultSet.getLong("id"))
                        .vacina(resultSet.getString("vacina"))
                        .bairro(resultSet.getString("bairro"))
                        .dose(getDose(resultSet.getString("dose")))
                        .aplicador(resultSet.getString("aplicador"))
                        .dataAplicacao(DateUtils.parseTimestampToString(resultSet.getTimestamp("data")))
                        .observacoes(resultSet.getString("obs"))
                        .build());
            }
        } finally {
            conexao.desconectar(conn);
        }

        return vacinas;
    }

    private String getDose(String dose) {
        return Objects.nonNull(dose) && !dose.isEmpty() ? DosagemEnum.findByValor(Integer.parseInt(dose)) : "";
    }
}
