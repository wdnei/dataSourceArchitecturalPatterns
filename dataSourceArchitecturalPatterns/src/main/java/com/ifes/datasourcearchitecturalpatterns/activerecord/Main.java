/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifes.datasourcearchitecturalpatterns.activerecord;

import java.sql.Connection;
import java.sql.DriverManager;
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

            Pessoa psJonnas = new Pessoa(conn);
            psJonnas.setIdade(19);
            psJonnas.setNome("Jonnas");
            psJonnas.insert();

            Pessoa ps = Pessoa.find(conn, "Jonnas");

            if (ps != null) {
                System.out.println("Nome:" + ps.getNome());
                System.out.println("Idade:" + ps.getIdade() + "n");

            } else {
                System.out.println("Pessoa não existe");

            }

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
