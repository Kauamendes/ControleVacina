package com.example.demo.repository;

import com.example.demo.config.Conexao;
import com.example.demo.domain.Usuario;
import com.example.demo.dto.AlteracaoSenhaDto;
import com.example.demo.dto.LoginDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UsuarioRepository {

    @Cacheable("usuarios")
    public Usuario findByAccess(LoginDto login) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Usuario usuario = null;

        String query = "SELECT * FROM USUARIO WHERE LOWER(LOGIN) = LOWER(?) AND SENHA = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, login.getLogin());
            ps.setString(2, login.getSenha());

            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {
                usuario = new Usuario();
                usuario.setId(resultado.getLong("id"));
                usuario.setLogin(resultado.getString("login"));
                usuario.setSenha(resultado.getString("senha"));
                usuario.setCargo(resultado.getString("cargo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Usuario não encontrado");
        } finally {
            conexao.desconectar(conn);
        }
        return usuario;
    }

    @Cacheable("usuarios")
    public Usuario findByLogin(String login) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Usuario usuario = null;

        String query = "SELECT * FROM USUARIO WHERE LOWER(LOGIN) = LOWER(?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, login);

            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {
                usuario = new Usuario();
                usuario.setId(resultado.getLong("id"));
                usuario.setLogin(resultado.getString("login"));
                usuario.setSenha(resultado.getString("senha"));
                usuario.setCargo(resultado.getString("cargo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Usuario não encontrado");
        } finally {
            conexao.desconectar(conn);
        }
        return usuario;
    }

    public void updateUsuario(Usuario usuario) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();

        String query = "UPDATE USUARIO SET SENHA = ? WHERE ID = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, usuario.getSenha());
            ps.setLong(2, usuario.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao executar a atualização do usuário");
        } finally {
            conexao.desconectar(conn);
        }
    }

    public void insert(Usuario usuario) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();

        try {
            String query = "INSERT INTO USUARIO (LOGIN, SENHA, CARGO) VALUES(?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, usuario.getLogin());
            ps.setString(2, usuario.getSenha());
            ps.setString(3, usuario.getCargo());
            ps.execute();
            ps.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            conexao.desconectar(conn);
        }
    }
}
