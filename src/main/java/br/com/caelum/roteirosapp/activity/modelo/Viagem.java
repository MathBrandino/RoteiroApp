package br.com.caelum.roteirosapp.activity.modelo;

import java.io.Serializable;

/**
 * Created by matheus on 08/06/15.
 */
public class Viagem implements Serializable {

    Long id;
    String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    @Override
    public String toString() {
        return getId() +" - "+ getNome();
    }
}
