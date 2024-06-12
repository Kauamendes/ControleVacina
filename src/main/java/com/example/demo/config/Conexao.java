package com.example.demo.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public Connection conectar() {
        Connection conn = null;
        try {
            String dbUrl = "jdbc:postgresql://54.166.219.179:5432/cadvacinas";
            String dbUser = "postgres";
            String dbPass = "senhacadvacinas";

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