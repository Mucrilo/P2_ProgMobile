package com.example.filmeflix;

import java.io.Serializable;

public class Categoria implements Serializable {
    public int idcategoria;
    public String descricao;

    public int getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "id: "+ idcategoria+ " Categoria: " + descricao;
    }
}
