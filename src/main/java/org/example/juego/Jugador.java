package org.example.juego;

import org.example.algoritmo.Filtro;
import org.example.objetos.Personaje;

// Todo el acceso al secreto de un jugador pasa por esta interfaz: ni el
// rival, ni el algoritmo greedy de la IA, ni el código que orquesta la
// partida pueden leer directamente el campo "secreto" de otra
// implementación. Solo se puede preguntar (responder) o verificar un
// candidato puntual (esElSecreto); el valor real queda encapsulado dentro
// de cada clase (Humano / MaquinaIA).
public interface Jugador {

    boolean responder(Filtro filtro);

    boolean esElSecreto(Personaje candidato);

    Personaje getSecretoParaMostrar();

    String getNombreJugador();
}
