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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Classe Implementa o padrão de acesso a dados Row Data Gateway
 * @author admindev
 */
public class PessoaGateway {

    private Connection conn;
    private Statement stm;

    private Integer id;
    private int idade;
    private Integer dependentes;
    private String nome;

    private boolean inserted;
    private boolean deleted;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return this.idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
    
    
    /**
     * @return the dependentes
     */
    public Integer getDependentes() {
        return dependentes;
    }

    /**
     * @param dependentes the dependentes to set
     */
    public void setDependentes(Integer dependentes) {
        this.dependentes = dependentes;
    }

    public PessoaGateway(Connection conn) throws SQLException, ClassNotFoundException {
        inserted = false;
        deleted = false;
        Class.forName("org.sqlite.JDBC");
        this.conn = conn;
        this.stm = this.conn.createStatement();
        //Init db
        try {
//Remove e cria a tabela a cada execução. Mero exemplo
//            this.stm.executeUpdate("DROP TABLE IF EXISTS pessoas");
            this.stm.executeUpdate("CREATE TABLE IF NOT EXISTS pessoas ("
                    + "id integer PRIMARY KEY NOT NULL,"
                    + "nome varchar(70) NOT NULL,"
                    + "idade integer,"
                    + "dependentes integer);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert() throws SQLException {
        this.stm = this.conn.createStatement();
        this.stm.executeUpdate("INSERT INTO pessoas VALUES ("
                + id + ",\""
                + nome + "\","
                +idade+","
                + dependentes + ")");
        inserted = true;
    }

    public void update() throws SQLException {
        this.stm = this.conn.createStatement();

        this.stm.executeUpdate("UPDATE pessoas"
                + "SET nome=\"" + nome + "\",idade=" + idade + ",dependentes="+dependentes
                + " WHERE id=" + id + "");
    }

    public void delete() throws SQLException {
        this.stm = this.conn.createStatement();
        this.stm.executeUpdate("DELETE FROM pessoas WHERE id = " + id + "");
        deleted = true;
        inserted=false;
    }

    public static PessoaGateway load(ResultSet rs) throws SQLException, ClassNotFoundException {

        PessoaGateway pessoa = null;

        pessoa = new PessoaGateway(rs.getStatement().getConnection());
        pessoa.inserted = true;
        pessoa.id = rs.getInt("id");
        pessoa.nome = rs.getString("nome");
        pessoa.idade = rs.getInt("idade");
        pessoa.dependentes=rs.getInt("dependentes");

        return pessoa;
    }

    /**
     * @return the inserted
     */
    public boolean isInserted() {
        return inserted;
    }

    /**
     * @return the deleted
     */
    public boolean isDeleted() {
        return deleted;
    }


}
