package com.example.demo.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public Connection conectar() {
        Connection conn = null;
        try {
            String dbUrl = System.getenv("DB_URL");
            String dbUser = System.getenv("DB_USER");
            String dbPass = System.getenv("DB_PASS");

            if (dbUrl == null || dbUser == null || dbPass == null) {
                throw new IllegalArgumentException("Variáveis de ambiente DB_URL, DB_USER ou DB_PASS não estão definidas.");
            }

            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        } catch (SQLException ex) {
            System.out.println("Erro: Não conseguiu conectar no BD.");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro: Não encontrou o driver do BD.");
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
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
            ex.printStackTrace();
        }
    }
}