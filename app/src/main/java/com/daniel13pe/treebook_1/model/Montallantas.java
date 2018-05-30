package com.daniel13pe.treebook_1.model;

public class Montallantas {
    String foto, nombre, valor, id, nombrecien, descrip;

    public Montallantas() {
    }

    public Montallantas(String foto, String nombre, String valor, String id, String nombrecien, String descrip) {
        this.id = id;
        this.foto = foto;
        this.nombre = nombre;
        this.valor = valor;
        this.nombrecien = nombrecien;
        this.descrip = descrip;
    }

    public String getDescrip() { return descrip;
    }

    public void setDescrip(String descrip) { this.descrip = descrip;
    }

    public String getNombrecien() {
        return nombrecien;
    }

    public void setNombrecien(String nombrecien) {
        this.nombrecien = nombrecien;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
