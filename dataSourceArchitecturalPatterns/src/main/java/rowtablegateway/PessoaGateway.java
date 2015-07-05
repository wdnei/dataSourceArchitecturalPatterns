/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rowtablegateway;

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
public class PessoaGateway {

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

    public PessoaGateway(Connection conn) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.conn = conn;
        this.stm = this.conn.createStatement();
        //Init db
        try {
//Remove e cria a tabela a cada execução. Mero exemplo
//            this.stm.executeUpdate("DROP TABLE IF EXISTS pessoas");
            this.stm.executeUpdate("CREATE TABLE IF NOT EXISTS pessoas ("
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
            System.out.println("inseriu:"+nome);
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

    public static PessoaGateway load(ResultSet rs )  {

        PessoaGateway pessoa = null;
        try {
            pessoa = new PessoaGateway(rs.getStatement().getConnection());

            pessoa.nome = rs.getString("nome");
            pessoa.idade = rs.getInt("idade");

            
        } catch (Exception ex) {
            Logger.getLogger(PessoaGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pessoa;
    }

}
