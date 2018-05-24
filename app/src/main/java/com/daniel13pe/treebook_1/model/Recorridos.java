package com.daniel13pe.treebook_1.model;

public class Recorridos {
    String id, foto, inicio, fin, fecha;

    public Recorridos(){ }

    public Recorridos(String id, String foto, String inicio, String fin, String fecha) {
        this.id = id;
        this.foto = foto;
        this.inicio = inicio;
        this.fin = fin;
        this.fecha = fecha;
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

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
