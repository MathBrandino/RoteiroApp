package br.com.caelum.roteirosapp.activity.modelo;

import java.io.Serializable;

/**
 * Created by matheus on 08/06/15.
 */
public class Parada implements Serializable {

    Long id;
    String descricao;
    String caminhoDaFoto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCaminhoDaFoto() {
        return caminhoDaFoto;
    }

    public void setCaminhoDaFoto(String caminhoDaFoto) {
        this.caminhoDaFoto = caminhoDaFoto;
    }

    @Override
    public String toString() {
        return descricao;
    }


}
