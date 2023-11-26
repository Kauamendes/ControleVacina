package com.example.demo.repository;

import com.example.demo.config.Conexao;
import com.example.demo.domain.Bairro;
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

    public List<Bairro> listarBairros() {
        List<Bairro> bairros = new ArrayList<>();
        bairros.add(Bairro.builder().id(1L).nome("Bom pastor").build());
        bairros.add(Bairro.builder().id(2L).nome("humaitá de cima").build());
        return bairros;
    }

    public Bairro buscarBairroPorNome(String nomeBairro) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Bairro bairro = new Bairro();
        String sql = "SELECT * FROM BAIRRO WHERE NOME LIKE'%?%'";
        try {
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(sql);

            while (resultado.next()) {
                bairro.setId(resultado.getLong("id"));
                bairro.setNome(resultado.getString("nome"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar bairro por nome: "+e.getMessage());
        } finally {
            conexao.desconectar(conn);
        }
        return bairro;
    }
}
