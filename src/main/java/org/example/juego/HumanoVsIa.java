package org.example.juego;

import org.example.algoritmo.BuscarGanador;
import org.example.algoritmo.TurnoHumano;
import org.example.algoritmo.TurnoIa;
import org.example.bd.Generador;
import org.example.objetos.Personaje;

import java.util.List;

public class HumanoVsIa {

    private boolean terminado = false;

    private Generador generador = new Generador();
    private BuscarGanador buscarGanador = new BuscarGanador();
    private TurnoHumano turnoHumano = new TurnoHumano();
    private TurnoIa turnoIa = new TurnoIa();

    private List<Personaje> humano;
    private List<Personaje> ia;

    private Personaje personajeHumano;

    // Inicia el juego
    public void iniciarJuego() {

        humano = generador.generar();
        ia = generador.generar();

        personajeHumano = buscarGanador.buscarImpostor(humano);

        terminado = false;
    }

    // Turno del humano
    public Personaje jugarHumano(String caracteristica, int respuesta) {

        Personaje resultado =
                turnoHumano.turno(ia, caracteristica, respuesta);

        if (resultado != null) {
            terminado = true;
        }

        return resultado;
    }

    // Turno de la IA
    public Personaje jugarIa() {

        Personaje resultado = turnoIa.turno(humano);

        if (resultado != null) {
            terminado = true;
        }

        return resultado;
    }

    // Devuelve los personajes que está intentando descubrir el humano
    public List<Personaje> getPersonajesIa() {
        return ia;
    }

    // Devuelve los personajes que está intentando descubrir la IA
    public List<Personaje> getPersonajesHumano() {
        return humano;
    }

    // Personaje que le tocó al humano
    public Personaje getPersonajeHumano() {
        return personajeHumano;
    }

    public boolean isTerminado() {
        return terminado;
    }
}