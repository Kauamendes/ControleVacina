package com.example.demo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.example.demo.config.Conexao;
import com.example.demo.domain.Usuario;

@Repository
public class UsuarioRepository {

    public boolean findByLogin(Usuario login) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        boolean logado = false;

        String query = "SELECT EXISTS(SELECT 1 FROM USUARIO WHERE LOGIN = ? AND SENHA = ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, login.getLogin());
            ps.setString(2, login.getSenha());

            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {
                logado = resultado.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Usuario não encontrado");
        } finally {
            conexao.desconectar(conn);
        }
        return logado;
    }
}
