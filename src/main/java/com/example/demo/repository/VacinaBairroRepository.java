package com.example.demo.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.domain.Bairro;
import com.example.demo.domain.Vacina;
import org.springframework.stereotype.Repository;

import com.example.demo.config.Conexao;
import com.example.demo.domain.VacinaBairro;

@Repository
public class VacinaBairroRepository {

    public void insert(VacinaBairro vacinaBairro) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();

        try {
            String query = "INSERT INTO VACINA_BAIRRO (VACINA_ID, BAIRRO_ID) VALUES(?, ?)";

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

    public List<Bairro> listarBairros() {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Bairro> bairros = new ArrayList<>();
        String sql = "SELECT * FROM BAIRRO ORDER BY NOME";
        try {
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(sql);

            while (resultado.next()) {
                Bairro bairro = Bairro.builder().build();
                bairro.setId(resultado.getLong("id"));
                bairro.setNome(resultado.getString("nome"));
                bairros.add(bairro);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar bairros: " + e.getMessage());
        } finally {
            conexao.desconectar(conn);
        }
        return bairros;
    }

    public List<Vacina> listarVacinas() {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Vacina> vacinas = new ArrayList<>();
        String sql = "SELECT * FROM VACINA ORDER BY NOME";
        try {
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(sql);

            while (resultado.next()) {
                Vacina vacina = Vacina.builder().build();
                vacina.setId(resultado.getLong("id"));
                vacina.setNome(resultado.getString("nome"));
                vacina.setDosagem(resultado.getBoolean("dosagem"));
                vacinas.add(vacina);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar vacinas: " + e.getMessage());
        } finally {
            conexao.desconectar(conn);
        }
        return vacinas;
    }

    public Bairro buscarBairroPorId(Long id) throws SQLException {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();

        PreparedStatement preparedStatement = conn.prepareStatement("SELECT id, nome FROM BAIRRO WHERE id = ?");
        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Bairro bairro = new Bairro();
            bairro.setId(resultSet.getLong("id"));
            bairro.setNome(resultSet.getString("nome"));
            return bairro;
        }
        return null;
    }
}
