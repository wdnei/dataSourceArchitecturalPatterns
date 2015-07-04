/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifes.datasourcearchitecturalpatterns;

import java.util.Iterator;

/**
 *
 * @author admindev
 */
public class Main {

    public static void main(String[] args) {
        try {
            SQLite dbCon = new SQLite("pessoas.db");

            dbCon.initDB();
            dbCon.insert(new Pessoa("Jonnas", 19));
            dbCon.insert(new Pessoa("Fulano", 20));
            dbCon.insert(new Pessoa("Beltrano", 10));

            Main.listaTodos(dbCon);

            System.out.println("Removemos a pessoa com o nome Fulano e listamos novamente");
            dbCon.removePessoa("Fulano");

            Main.listaTodos(dbCon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void listaTodos(SQLite dbCon) {
        Iterator it = dbCon.getAll().iterator();
        Pessoa hs;
        while (it.hasNext()) {
            hs = (Pessoa) it.next();
            System.out.println("Nome:" + hs.getNome());
            System.out.println("Idade:" + hs.getIdade() + "n");
        }
    }

}
