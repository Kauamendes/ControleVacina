package com.example.demo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.example.demo.config.Conexao;

@Repository
public class BairroRepository {

    // REMOVER
    public boolean existsById(Long id) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        boolean isExistente = false;

        String query = "SELECT EXISTS(SELECT 1 FROM BAIRRO WHERE ID = ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, id);

            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {
                isExistente = resultado.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Bairro não encontrado");
        } finally {
            conexao.desconectar(conn);
        }
        return isExistente;
    }

}
