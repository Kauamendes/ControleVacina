package com.example.demo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.springframework.stereotype.Repository;

import com.example.demo.config.Conexao;
import com.example.demo.domain.Vacina;

@Repository
public class VacinaRepository {

    public void insert(Vacina vacina) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();

        try {
            String query = "INSERT INTO VACINA (VACINA_ID, NOME)" +
                    " VALUES(?,?)";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, vacina.getId());
            ps.setString(2, vacina.getNome());
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            conexao.desconectar(conn);
        }
    }

}
