package com.example.demo.repository;

import com.example.demo.config.Conexao;
import com.example.demo.domain.Vacina;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VacinaRepository {

    public List<Vacina> listarVacinas() throws SQLException {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Vacina> vacinas = new ArrayList<>();
        String sql = "SELECT * FROM VACINA";
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
            System.out.println("Erro ao listar vacinas: "+e.getMessage());
        } finally {
            conexao.desconectar(conn);
        }
        return vacinas;
    }

}
