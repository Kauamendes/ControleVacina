package com.example.demo.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.domain.Bairro;
import com.example.demo.domain.Vacina;
import org.springframework.stereotype.Repository;

import com.example.demo.config.Conexao;
import com.example.demo.domain.VacinaBairro;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VacinaBairroRepository {

    public void insert(VacinaBairro vacinaBairro) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();

        try {
            String query = "INSERT INTO VACINA_BAIRRO (VACINA_ID, BAIRRO_ID, DATA_APLICACAO)" +
                    " VALUES(?,?,?)";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, vacinaBairro.getVacinaId());
            ps.setLong(2, vacinaBairro.getBairroId());
            ps.execute();
            ps.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            conexao.desconectar(conn);
        }
    }
}
