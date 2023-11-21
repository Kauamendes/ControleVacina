package com.example.demo.repository;

import com.example.demo.config.Conexao;
import com.example.demo.dto.RelatorioDto;
import com.example.demo.dto.VacinaBairroDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RelatorioRepository {
    //List<VacinaBairroDto>
    public String buscar(RelatorioDto relatorioDto) {
        List<VacinaBairroDto> vacinas = new ArrayList<>();
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        String resultado = "";

        if (relatorioDto.getBairro().isBlank()) {
            relatorioDto.setBairro(null);
        }
        if (relatorioDto.getDataInicio().isBlank()) {
            relatorioDto.setDataInicio(null);
        }
        if (relatorioDto.getDataFim().isBlank()) {
            relatorioDto.setDataFim(null);
        }

        String query = " SELECT a.id as id_vacina, a.nome as nome_vacina, " +
                " c.nome as bairro_nome, b.data_aplicacao " +
                " FROM vacina a " +
                " INNER JOIN vacina_bairro b ON b.vacina_id = a.id " +
                " INNER JOIN bairro c ON c.id = b.bairro_id " +
                " WHERE ("+ relatorioDto.getBairro() +" IS NULL OR "+ relatorioDto.getBairro() +" = c.nome)" +
                " AND ('" + relatorioDto.getDataInicio() + "' IS NULL OR '" + relatorioDto.getDataInicio() + "' > b.data_aplicacao) " +
                " AND ('" + relatorioDto.getDataFim() + "' IS NULL OR '" + relatorioDto.getDataFim() + "' < b.data_aplicacao) ";


        try{
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return resultado;
    }
}
