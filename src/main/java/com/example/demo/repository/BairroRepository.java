package com.example.demo.repository;

import com.example.demo.config.Conexao;
import com.example.demo.domain.Bairro;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BairroRepository {

    public List<Bairro> listarBairros() throws SQLException {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Bairro> bairros = new ArrayList<>();
        String sql = "SELECT * FROM BAIRRO";
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

    public Bairro buscarBairroPorNome(String nomeBairro) throws SQLException {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();

        PreparedStatement preparedStatement = conn.prepareStatement("SELECT id, nome FROM BAIRRO WHERE NOME LIKE ? ");
        preparedStatement.setString(1, nomeBairro);

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
