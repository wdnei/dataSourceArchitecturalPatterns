/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifes.datasourcearchitecturalpatterns.tabledatagateway;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admindev
 */
public class PessoaGateway {

    private Connection conn;
    private Statement stm;

    public PessoaGateway(Connection conn) throws SQLException, ClassNotFoundException {
        this.conn = conn;
        this.stm = this.conn.createStatement();
        //Init db
        try {
//Remove e cria a tabela a cada execução. Mero exemplo
            this.stm.executeUpdate("DROP TABLE IF EXISTS pessoas");
            this.stm.executeUpdate("CREATE TABLE pessoas ("
                    + "nome varchar(70) PRIMARY KEY NOT NULL,"
                    + "idade integer);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initDB() {
        try {
//Remove e cria a tabela a cada execução. Mero exemplo
            this.stm.executeUpdate("DROP TABLE IF EXISTS pessoas");
            this.stm.executeUpdate("CREATE TABLE pessoas ("
                    + "nome varchar(70) PRIMARY KEY NOT NULL,"
                    + "idade integer);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(String nome, int idade) {
        try {
            this.stm = this.conn.createStatement();
            this.stm.executeUpdate("INSERT INTO pessoas VALUES (\""
                    + nome + "\","
                    + idade + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removePessoa(String nome) {
        try {
            this.stm = this.conn.createStatement();
            this.stm.executeUpdate("DELETE FROM pessoas WHERE nome = \"" + nome + "\"");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vector getAll() {
        Vector lista = new Vector();
        ResultSet rs;
        try {

            rs = this.stm.executeQuery("SELECT * FROM pessoas ORDER BY idade");

            while (rs.next()) {
                Object[] values = new Object[rs.getMetaData().getColumnCount()];
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    values[i - 1] = rs.getObject(i);
                }
                lista.add(values);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PessoaGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

}
