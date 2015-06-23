package br.com.caelum.roteirosapp.activity.modelo;

import java.io.Serializable;

/**
 * Created by matheus on 08/06/15.
 */
public class Viagem implements Serializable {

    private Long id;
    private String nome;
    private String dataInicio;
    private String dataFinal;

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }

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
}
