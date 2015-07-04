/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifes.datasourcearchitecturalpatterns.activerecord;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admindev
 */
public class Pessoa {

    private Connection conn;
    private Statement stm;

    private int idade;
    private String nome;

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
    
    public boolean podeSerPreso()
    {
        return this.idade>18;
    }
    

    public Pessoa(Connection conn) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
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

    public void insert() {
        try {
            this.stm = this.conn.createStatement();
            this.stm.executeUpdate("INSERT INTO pessoas VALUES (\""
                    + nome + "\","
                    + idade + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removePessoa() {
        try {
            this.stm = this.conn.createStatement();
            this.stm.executeUpdate("DELETE FROM pessoas WHERE nome = \"" + nome + "\"");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Pessoa load(ResultSet rs, Connection conn)  {

        Pessoa pessoa = null;
        try {
            pessoa = new Pessoa(conn);

            pessoa.nome = rs.getString("nome");
            pessoa.idade = rs.getInt("idade");

            
        } catch (Exception ex) {
            Logger.getLogger(Pessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pessoa;
    }
    public final static Pessoa find(Connection conn,String nome) throws ClassNotFoundException, SQLException
    {
        Pessoa.initDB(conn);
        Statement stm=conn.createStatement();
        Pessoa ps=null;
        ResultSet rs;
        try {
            rs = stm.executeQuery("SELECT * FROM pessoas WHERE nome=\"" +nome+"\"");

            while (rs.next()) {
                ps=new Pessoa(conn);
                ps.setNome(rs.getString("nome"));
                ps.setIdade(rs.getInt("idade"));
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }
    private final static void initDB(Connection conn) {
        try {
//Remove e cria a tabela a cada execução. Mero exemplo
            Statement stm=conn.createStatement();
            stm.executeUpdate("DROP TABLE IF EXISTS pessoas");
            stm.executeUpdate("CREATE TABLE pessoas ("
                    + "nome varchar(70) PRIMARY KEY NOT NULL,"
                    + "idade integer);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
