package com.example.demo.repository;

import com.example.demo.config.Conexao;
import com.example.demo.domain.Bairro;
import com.example.demo.domain.Vacina;
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
            ps.setDate(3, vacinaBairro.getDataAplicacao());
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            conexao.desconectar(conn);
        }
    }

    public Bairro buscarBairroPorNome(String nomeBairro) throws SQLException {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Bairro bairro = new Bairro();
        String sql = "SELECT * FROM BAIRRO WHERE NOME LIKE ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, nomeBairro);
            ResultSet resultado = ps.executeQuery();

            while (resultado.next()) {
                bairro.setId(resultado.getLong("id"));
                bairro.setNome(resultado.getString("nome"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar bairro por nome: "+e.getMessage());
        } finally {
            conexao.desconectar(conn);
            if (ps != null) {
                ps.close();
            }
        }
        return bairro;
    }

    public List<Vacina> listarVacinas() {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();

        List<Vacina> vacinas = new ArrayList<>();

        try {
            String sql = "SELECT * FROM VACINA";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet resultado = ps.executeQuery();

            while (resultado.next()) {
                vacinas.add(Vacina.builder()
                        .id(resultado.getLong("id"))
                        .nome(resultado.getString("nome"))
                        .build());
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar vacinas: " + e.getMessage());
        } finally {
            conexao.desconectar(conn);
        }

        return vacinas;
    }


    public List<Bairro> listarBairros() {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();

        List<Bairro> bairros = new ArrayList<>();

        try {
            String sql = "SELECT * FROM BAIRRO";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet resultado = ps.executeQuery();

            while (resultado.next()) {
                bairros.add(Bairro.builder()
                        .id(resultado.getLong("id"))
                        .nome(resultado.getString("nome"))
                        .build());
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar bairros: " + e.getMessage());
        } finally {
            conexao.desconectar(conn);
        }

        return bairros;
    }

}
