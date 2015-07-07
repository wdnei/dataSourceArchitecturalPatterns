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

/**
 *Classe Implementa o padrão de acesso a dados Table Data Gateway
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
            // this.stm.executeUpdate("DROP TABLE IF EXISTS pessoas");
            this.stm.executeUpdate("CREATE TABLE IF NOT EXISTS pessoas ("
                    + "id integer PRIMARY KEY NOT NULL,"
                    + "nome varchar(70) NOT NULL,"
                    + "idade integer,"
                    + "dependentes integer);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initDB() {
        try {
//Remove e cria a tabela a cada execução. Mero exemplo
            //this.stm.executeUpdate("DROP TABLE IF EXISTS pessoas");
            this.stm.executeUpdate("CREATE TABLE IF NOT EXISTS pessoas ("
                    + "id integer PRIMARY KEY NOT NULL,"
                    + "nome varchar(70) NOT NULL,"
                    + "idade integer,"
                    + "dependentes integer);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(int id, String nome, int idade,int dependentes) throws SQLException {

        this.stm = this.conn.createStatement();
        this.stm.executeUpdate("INSERT INTO pessoas VALUES ("
                + id + ",\""
                + nome + "\","
                +idade+","
                + dependentes + ")");

    }

    public void delete(int id) throws SQLException {

        this.stm = this.conn.createStatement();
        this.stm.executeUpdate("DELETE FROM pessoas WHERE id = " + id + "");

    }

    public void update(int id, String nome, int idade, int dependentes) throws SQLException {

        this.stm = this.conn.createStatement();

        this.stm.executeUpdate("UPDATE pessoas"
                + "SET nome=\"" + nome + "\",idade=" + idade + ",dependentes="+dependentes
                + " WHERE id=" + id + "");

    }

    public ResultSet getAll() throws SQLException {

        ResultSet rs = null;

        rs = this.stm.executeQuery("SELECT * FROM pessoas ORDER BY idade");

        return rs;
    }

}
