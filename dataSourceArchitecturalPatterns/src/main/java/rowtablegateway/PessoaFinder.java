/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rowtablegateway;

import com.ifes.datasourcearchitecturalpatterns.Pessoa;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author admindev
 */
public class PessoaFinder {
    
    private Connection conn;
    private Statement stm;
    
    
    public PessoaFinder(Connection conn) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.conn=conn;
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

    
    public PessoaGateway find(String nome) throws ClassNotFoundException
    {
        PessoaGateway ps=null;
        ResultSet rs;
        try {
            rs = this.stm.executeQuery("SELECT * FROM pessoas WHERE nome=\"" +nome+"\"");

            while (rs.next()) {
                ps=new PessoaGateway(conn);
                ps.setNome(rs.getString("nome"));
                ps.setIdade(rs.getInt("idade"));
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }
    
}
