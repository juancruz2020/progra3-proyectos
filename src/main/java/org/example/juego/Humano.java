package org.example.juego;

import org.example.algoritmo.Filtro;
import org.example.objetos.Personaje;

public class Humano implements Jugador {

    private Personaje secreto;

    public void setSecreto(Personaje secreto) {
        this.secreto = secreto;
    }

    @Override
    public boolean responder(Filtro filtro) {
        return filtro.valorDe(secreto);
    }

    @Override
    public boolean esElSecreto(Personaje candidato) {
        return secreto != null && candidato != null && secreto.getId() == candidato.getId();
    }

    @Override
    public Personaje getSecretoParaMostrar() {
        return secreto;
    }

    @Override
    public String getNombreJugador() {
        return "Humano";
    }
}
