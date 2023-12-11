package com.example.demo.repository;

import com.example.demo.config.Conexao;
import com.example.demo.domain.Bairro;
import com.example.demo.domain.Vacina;
import com.example.demo.domain.VacinaBairro;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println("Erro ao listar bairros: "+e.getMessage());
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
                vacinas.add(vacina);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar vacinas: "+e.getMessage());
        } finally {
            conexao.desconectar(conn);
        }
        return vacinas;
    }
}
