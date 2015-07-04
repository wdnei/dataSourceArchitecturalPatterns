/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifes.datasourcearchitecturalpatterns.datamapper;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author admindev
 */
@Entity
@Table(name = "Pessoas")
public class Pessoa {

    private Integer id;
    private Integer idade;
    private String nome;

    @Id
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

    public boolean podeSerPreso() {
        return this.idade > 18;
    }

    public Pessoa(Integer id,String nome, Integer idade) {
        this.nome = nome;
        this.idade = idade;
        this.id=id;
    }

}
