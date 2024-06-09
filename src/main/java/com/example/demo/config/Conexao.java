package com.example.demo.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public Connection conectar() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://dpg-cpi76rcf7o1s73bd9bv0-a.oregon-postgres.render.com/cadvacinas_gypf", "cad_vacinas_senac_user", "NN0ZJ14TXeNj3lWvAXhqjW8I5vjmzMj2");
        } catch (SQLException ex) {
            System.out.println("Erro: Não conseguiu conectar no BD.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro: Não encontrou o driver do BD.");
        }
        return conn;
    }

    public void desconectar(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("Não conseguiu desconectar do BD.");
        }
    }
}
