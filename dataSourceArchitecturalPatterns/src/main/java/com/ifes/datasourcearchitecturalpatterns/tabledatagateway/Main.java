/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifes.datasourcearchitecturalpatterns.tabledatagateway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admindev
 */
public class Main {

    public static void main(String[] args) {

        try {

            Connection conn;
            Class.forName("org.sqlite.JDBC");
            
             conn = DriverManager.getConnection("jdbc:sqlite:pessoas.db");
           conn.createStatement().executeUpdate("DROP TABLE IF EXISTS pessoas");

            PessoaGateway pg = new PessoaGateway(conn);

            pg.insert(1,"Jonnas", 19);
            pg.insert(2,"Fulano", 20);
            pg.insert(3,"Beltrano", 10);

            Main.listaTodos(pg);

            System.out.println("Removemos a pessoa com o nome Fulano e listamos novamente");
            pg.delete(2);

            Main.listaTodos(pg);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void listaTodos(PessoaGateway pg) throws SQLException {
        ResultSet rs = pg.getAll();
        if (rs != null) {
            while (rs.next()) {
                System.out.println("Nome:" + rs.getString("nome"));
                System.out.println("Idade:" + rs.getString("idade") + "\n");
            }
            rs.close();
            
        }

    }

}
