package org.example.objetos;

public class Caracteristica {

    private int cantidad;
    private boolean valor;

    public Caracteristica() {
        this.cantidad = 0;
        this.valor = false;
    }

    public Caracteristica(int cantidad, boolean valor) {
        this.cantidad = cantidad;
        this.valor = valor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isValor() {
        return valor;
    }

    public void setValor(boolean valor) {
        this.valor = valor;
    }
}