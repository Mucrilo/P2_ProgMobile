package com.example.filmeflix;

import java.io.Serializable;

public class Filme implements Serializable {
    private int idFilme;
    private int idcategoria;
    private String titulo;
    private int ano;

    private int avaliacao;
    private String tempo;

    public int getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(int idFilme) {
        this.idFilme = idFilme;
    }

   public int getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    @Override
    public String toString() {
        return "Titulo: " + titulo +
                ", Ano de lançamento: " + ano +
                ", Avaliacao: " + avaliacao +
                ", Tempo de duração: " + tempo;
    }
}
