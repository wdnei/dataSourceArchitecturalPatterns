/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifes.datasourcearchitecturalpatterns.tabledatagateway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
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
            
            PessoaGateway pg = new PessoaGateway(conn);
            
            
            pg.insert("Jonnas", 19);
            pg.insert("Fulano", 20);
            pg.insert("Beltrano", 10);

            Main.listaTodos(pg);

            System.out.println("Removemos a pessoa com o nome Fulano e listamos novamente");
            pg.removePessoa("Fulano");

            Main.listaTodos(pg);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }

    public static void listaTodos(PessoaGateway pg) {
        Iterator it = pg.getAll().iterator();
        Object[] ps;
        while (it.hasNext()) {
            ps = (Object[]) it.next();
            System.out.println("Nome:" + (String)ps[0]);
            System.out.println("Idade:" + (int)ps[1] + "\n");
        }
    }

}
