package com.example.demo.repository;

import com.example.demo.config.Conexao;
import com.example.demo.domain.Usuario;
import com.example.demo.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.springframework.security.crypto.password.PasswordEncoder.*;

@Repository
public class UsuarioRepository {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public Usuario findByAccess(LoginDto login) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Usuario usuario = null;

        String query = "SELECT * FROM USUARIO WHERE LOGIN = ? AND SENHA = ?";
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

    public Usuario findByLogin(String login) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Usuario usuario = null;

        String query = "SELECT * FROM USUARIO WHERE LOGIN = ?";
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
        String senhaEncreptada = passwordEncoder.encode(usuario.getSenha());
        System.out.println(senhaEncreptada);
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, senhaEncreptada);
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

        String senhaEncreptada = passwordEncoder.encode(usuario.getSenha());

        try {
            String query = "INSERT INTO USUARIO (LOGIN, SENHA, CARGO) VALUES(?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, usuario.getLogin());
            ps.setString(2, senhaEncreptada);
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
