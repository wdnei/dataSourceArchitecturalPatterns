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
 *Classe Implementa o padrão de acesso a dados  Active Record
 * @author admindev
 */
public class Pessoa {

    private Connection conn;
    private Statement stm;

    private Integer id;
    private Integer idade;
    private Integer dependentes;
    private String nome;
    
    
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
    
    /**
     * @return the idade
     */
    public Integer getIdade() {
        return idade;
    }

    /**
     * @param idade the idade to set
     */
    public void setIdade(Integer idade) {
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
    
    public boolean recebeAdicionalPorFilhos()
    {
        return dependentes>0;
    }

    
    

    public Pessoa(Connection conn) throws SQLException, ClassNotFoundException {
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

    public void insert() {
        try {
            this.stm = this.conn.createStatement();
            this.stm.executeUpdate("INSERT INTO pessoas VALUES ("
                + id + ",\""
                + nome + "\","
                +getIdade()+","
                + getDependentes() + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void update() {
        try {
            this.stm = this.conn.createStatement();
            
            this.stm.executeUpdate("UPDATE pessoas"
                + "SET nome=\"" + nome + "\",idade=" + idade + ",dependentes="+dependentes
                + " WHERE id=" + id + "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            this.stm = this.conn.createStatement();
            this.stm.executeUpdate("DELETE FROM pessoas WHERE id = " + id + "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Pessoa load(ResultSet rs)  {

        Pessoa pessoa = null;
        try {
            pessoa = new Pessoa(rs.getStatement().getConnection());

            pessoa.id=rs.getInt("id");
            pessoa.nome = rs.getString("nome");
            pessoa.setIdade((Integer) rs.getInt("idade"));
            pessoa.setDependentes(rs.getInt("dependentes"));
            

            
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
                ps.setId(rs.getInt("id"));
                ps.setNome(rs.getString("nome"));
                ps.setIdade(rs.getInt("idade"));
                ps.setDependentes(rs.getInt("dependentes"));
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
//            stm.executeUpdate("DROP TABLE IF EXISTS pessoas");
            stm.executeUpdate("CREATE TABLE IF NOT EXISTS pessoas ("
                    + "id integer PRIMARY KEY NOT NULL,"
                    + "nome varchar(70) NOT NULL,"
                    + "idade integer,"
                    + "dependentes integer);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

}
