/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifes.datasourcearchitecturalpatterns.rowdatagateway;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *Classe Implementa o padrão de acesso a dados Row Data Gateway
 * @author admindev
 */
public class PessoaFinder {

    private Connection conn;
    private Statement stm;

    public PessoaFinder(Connection conn) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.conn = conn;
        this.stm = this.conn.createStatement();
        //Init db
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

    public PessoaGateway find(String nome) throws ClassNotFoundException, SQLException {
        PessoaGateway ps = null;
        ResultSet rs;

        System.out.println(nome);
        rs = this.stm.executeQuery("SELECT * FROM pessoas WHERE nome='" + nome + "'");

        while (rs.next()) {

            ps = PessoaGateway.load(rs);

        }
        rs.close();

        return ps;
    }

}
